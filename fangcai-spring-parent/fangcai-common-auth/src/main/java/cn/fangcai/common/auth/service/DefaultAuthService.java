package cn.fangcai.common.auth.service;

import cn.fangcai.common.auth.dto.UserAuthInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author MouFangCai
 * @date 2024/8/19 23:28
 * @description
 */
@Slf4j
public class DefaultAuthService implements IAuthService {


    @Override
    public UserAuthInfo getById(Object userId) {
        log.warn("DefaultAuthService is enable!");
        return new UserAuthInfo(userId);
    }

    @Override
    public Set<String> listAuthCodeById(Object userId) {
        log.warn("DefaultAuthService is enable!");
        return new HashSet<>();
    }
}