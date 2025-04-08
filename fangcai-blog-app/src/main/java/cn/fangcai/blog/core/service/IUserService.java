package cn.fangcai.blog.core.service;


import cn.fangcai.blog.core.model.req.UserEditReq;
import cn.fangcai.blog.core.model.req.UserLoginReq;
import cn.fangcai.blog.core.model.req.UserPageReq;
import cn.fangcai.blog.core.model.res.UserRes;
import cn.fangcai.common.model.dto.FcPageRes;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2023-03-21
 */
public interface IUserService {

    UserRes getById(Integer userId);

    UserRes getByLoginName(String loginName);

    UserRes loginByName(UserLoginReq loginReq);

    UserRes loginByWxCode(String wxCode);

    UserRes register(String loginName, String wxOpenId, String password);

    Boolean editUser(UserEditReq uptReq);

    // 新增分页查询用户方法
    FcPageRes<UserRes> pageUser(UserPageReq pageReq);

    // 新增更新用户状态方法
    Boolean uptUserStatus(Integer userId, Boolean enabled);
}
