package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.config.BlogAppProperties;
import cn.fangcai.blog.consts.BlogErrorCodeEnum;
import cn.fangcai.blog.mapper.UserMapper;
import cn.fangcai.blog.mapper.UserRoleMapper;
import cn.fangcai.blog.mapstruct.UserConverter;
import cn.fangcai.blog.model.entity.User;
import cn.fangcai.blog.model.entity.UserRole;
import cn.fangcai.blog.model.req.UserLoginReq;
import cn.fangcai.blog.model.res.UserRes;
import cn.fangcai.blog.service.ICacheService;
import cn.fangcai.blog.service.IRoleService;
import cn.fangcai.blog.service.IUserService;
import cn.fangcai.blog.uitls.CacheKeyFactory;
import cn.fangcai.starter.auth.dto.UserAuthInfo;
import cn.fangcai.starter.auth.service.IAuthService;
import cn.fangcai.starter.auth.utils.FcPWDUtil;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2023-03-21
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService, IAuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private BlogAppProperties blogAppProperties;

    @Override
    public UserRes getById(Integer userId) {
        User user = userRepository.getById(userId);
        return UserConverter.INSTANCE.userToRes(user);
    }

    @Override
    public UserRes getByLoginName(String loginName) {
        User user = userRepository.lambdaQuery().eq(User::getLoginName, loginName).one();
        return UserConverter.INSTANCE.userToRes(user);
    }

    @Override
    public UserRes loginByName(UserLoginReq loginReq) {
        User user = userRepository.lambdaQuery()
                .eq(User::getLoginName, loginReq.getLoginName())
                .one();
        if (user == null) {
            throw new FcBusinessException(BlogErrorCodeEnum.LONGIN_NAME_OR_PASS_WORD_ERROR);
        }
        boolean isRight = FcPWDUtil.checkPwd(user.getId().toString(), loginReq.getPassword(), user.getPassword());
        if (!isRight) {
            throw new FcBusinessException(BlogErrorCodeEnum.LONGIN_NAME_OR_PASS_WORD_ERROR);
        }
        if (!user.getEnabled()) {
            throw new FcBusinessException(BlogErrorCodeEnum.USER_UN_ENABLED);
        }
        return UserConverter.INSTANCE.userToRes(user);
    }

    @Override
    public UserRes loginByWxCode(String wxCode) {
        String wxOpenId = cacheService.getAndDel(CacheKeyFactory.getWxLoginKey(wxCode));
        if (wxOpenId == null) {
            throw new FcBusinessException(BlogErrorCodeEnum.WX_CODE_EXPIRED);
        }
        User user = userRepository.lambdaQuery()
                .eq(User::getWxOpenId, wxOpenId)
                .one();
        if (user == null) {
            return this.register(wxOpenId, wxOpenId, RandomUtil.randomString(16));
        }
        if (!user.getEnabled()) {
            throw new FcBusinessException(BlogErrorCodeEnum.USER_UN_ENABLED);
        }
        return UserConverter.INSTANCE.userToRes(user);
    }

    @Override
    public <T extends UserAuthInfo> T getById(Object userId) {
        if (!(userId instanceof Integer)) {
            return null;
        }
        User user = userRepository.getById((Integer) userId);
        if (user != null && user.getEnabled()) {
            return (T) new UserAuthInfo(userId);
        }
        return null;
    }

    @Override
    public Set<String> listAuthCodeById(Object userId) {
        if (!(userId instanceof Integer)) {
            return new HashSet<>();
        }
        User user = userRepository.getById((Integer) userId);
        if (user != null && user.getEnabled()) {
            List<Integer> roleIds = userRoleRepository.lambdaQuery()
                    .eq(UserRole::getUserId, user.getId())
                    .list()
                    .stream()
                    .map(UserRole::getRoleId)
                    .toList();
            return roleService.listAuthCode(roleIds);
        }
        return new HashSet<>();
    }


    @Override
    public UserRes register(String loginName, String wxOpenId, String password) {
        if (StrUtil.isBlank(loginName) || StrUtil.isBlank(password) || StrUtil.isBlank(wxOpenId)) {
            log.warn("register 参数不完整，loginName:{}, wxOpenId:{}, password:{}", loginName, wxOpenId, password);
            return null;
        }
        User newUser = new User();
        newUser.setWxOpenId(wxOpenId);
        newUser.setLoginName(loginName);
        newUser.setNickName(LocalDate.now().getMonthValue() + "月，遇见你");
        newUser.setPassword("tempStr");
        userRepository.save(newUser);
        userRepository.lambdaUpdate()
                .eq(User::getId, newUser.getId())
                .set(User::getPassword, FcPWDUtil.encrypt(newUser.getId().toString(), password))
                .update();
        // 注册默认角色
        UserRole userRole = new UserRole();
        userRole.setUserId(newUser.getId());
        userRole.setRoleId(blogAppProperties.getDefaultRoleId());
        userRole.setOperator(newUser.getId());
        userRoleRepository.save(userRole);
        return UserConverter.INSTANCE.userToRes(newUser);
    }

    @Repository
    static class UserRepository extends ServiceImpl<UserMapper, User> {

    }

    @Repository
    static class UserRoleRepository extends ServiceImpl<UserRoleMapper, UserRole> {

    }
}
