package cn.fangcai.starter.auth;

import cn.fangcai.common.model.exception.FcBusinessException;
import cn.fangcai.common.model.uitls.SpringMVCUtil;
import cn.fangcai.starter.auth.config.AuthProperties;
import cn.fangcai.starter.auth.dto.ClientInfo;
import cn.fangcai.starter.auth.dto.UserTokenDto;
import cn.fangcai.starter.auth.service.IAuthService;
import cn.fangcai.starter.auth.utils.CookieUtil;
import cn.fangcai.starter.auth.utils.LoginHttpUtil;
import cn.hutool.extra.spring.SpringUtil;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:16
 * @description
 */
public class FcClientContext {

    private static final ThreadLocal<ClientInfo> CLIENT_INFO = new ThreadLocal<>();


    public static void initContext() throws FcBusinessException {
        ClientInfo clientInfo = new ClientInfo();
        String clientIdJwt = CookieUtil.getCookie(AuthProperties.CLIENT_COOKIE_NAME);
        //TODO : by mfc on 2024/11/30 待编写
        clientInfo.setClientIp(SpringMVCUtil.getClientIp(AuthProperties.CLIENT_IP_HEADER));
        CLIENT_INFO.set(clientInfo);
    }

    public static ClientInfo getClientInfo() {
        return CLIENT_INFO.get();
    }

    /**
     * 获取客户端ID，为每个浏览器会话 生成的唯一标识
     *
     * @return
     */
    public static String getClientId() {
        return getClientInfo().getClientId();
    }

    public static String getClientIp() {
        return getClientInfo().getClientIp();
    }

    public static void clearContext() {
        CLIENT_INFO.remove();
    }


}
