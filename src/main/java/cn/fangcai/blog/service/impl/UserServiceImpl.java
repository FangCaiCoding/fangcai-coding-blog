package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.consts.BlogErrorCodeEnum;
import cn.fangcai.blog.mapper.UserMapper;
import cn.fangcai.blog.mapstruct.UserConverter;
import cn.fangcai.blog.model.entity.User;
import cn.fangcai.blog.model.req.UserEmailRegisterReq;
import cn.fangcai.blog.model.req.UserLoginReq;
import cn.fangcai.blog.model.res.UserRes;
import cn.fangcai.blog.service.IUserService;
import cn.fangcai.common.auth.utils.FcPWDUtil;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2023-03-21
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserRes getById(Integer userId) {
        User user = userRepository.getById(userId);
        return UserConverter.INSTANCE.userToRes(user);
    }

    @Override
    public List<UserRes> listResByIds(List<Integer> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        List<User> userList = userRepository.listByIds(userIds);
        return UserConverter.INSTANCE.userListToResList(userList);
    }


    @Override
    public UserRes register(UserEmailRegisterReq registerReq) {
        String email = registerReq.getEmail();
        boolean loginNameExists = userRepository.lambdaQuery()
                .eq(User::getEmail, email)
                .exists();
        if (loginNameExists) {
            throw new FcBusinessException(BlogErrorCodeEnum.EMAIL_EXIST);
        }
        User newUser = new User();
        newUser.setLoginName(email);
        newUser.setEmail(email);
        newUser.setNickName(email);
        newUser.setPassword("TempValue");
        userRepository.save(newUser);
        userRepository.lambdaUpdate()
                .eq(User::getId, newUser.getId())
                .set(User::getPassword, FcPWDUtil.encrypt(newUser.getId().toString(), email))
                .update();
        return UserConverter.INSTANCE.userToRes(newUser);
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


    @Component
    public static class UserRepository extends ServiceImpl<UserMapper, User> {

    }
}
