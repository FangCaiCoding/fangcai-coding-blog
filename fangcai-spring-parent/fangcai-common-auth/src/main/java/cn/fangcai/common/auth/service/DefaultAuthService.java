package cn.fangcai.common.auth.service;

import cn.fangcai.common.auth.dto.UserAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author MouFangCai
 * @date 2024/8/19 23:28
 * @description
 */
@Service
@ConditionalOnMissingBean(value = IAuthService.class)
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
