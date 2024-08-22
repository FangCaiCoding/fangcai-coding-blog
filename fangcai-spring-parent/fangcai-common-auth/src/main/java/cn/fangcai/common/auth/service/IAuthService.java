package cn.fangcai.common.auth.service;

import cn.fangcai.common.auth.dto.UserAuthInfo;
import cn.fangcai.common.auth.dto.UserTokenDto;
import cn.fangcai.common.auth.utils.LoginHttpUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @author MouFangCai
 * @date 2024/8/19 23:28
 * @description
 */
public interface IAuthService<T> {


    public UserAuthInfo<T> getById(T userId);


}
