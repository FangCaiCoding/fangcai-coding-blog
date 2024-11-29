package cn.fangcai.starter.auth.service;

import cn.fangcai.starter.auth.dto.UserAuthInfo;

import java.util.Set;

/**
 * @author MouFangCai
 * @date 2024/8/19 23:28
 * @description
 */
public interface IAuthService {


    public <T extends UserAuthInfo> T getById(Object userId);


    public Set<String> listAuthCodeById(Object userId);


}
