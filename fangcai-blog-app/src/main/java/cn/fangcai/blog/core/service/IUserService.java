package cn.fangcai.blog.core.service;


import cn.fangcai.blog.core.model.res.UserRes;
import cn.fangcai.blog.core.model.req.UserLoginReq;

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
}
