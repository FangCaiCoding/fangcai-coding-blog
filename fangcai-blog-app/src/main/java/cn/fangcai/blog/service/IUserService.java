package cn.fangcai.blog.service;



import cn.fangcai.blog.model.req.UserEmailRegisterReq;
import cn.fangcai.blog.model.req.UserLoginReq;
import cn.fangcai.blog.model.res.UserRes;

import java.util.List;

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

    UserRes loginByName(UserLoginReq loginReq);

    UserRes loginByWxCode(String wxCode);
}
