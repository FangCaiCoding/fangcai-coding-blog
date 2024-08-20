package fc.common.auth.service;

import fc.common.auth.dto.UserTokenDto;
import fc.common.auth.utils.LoginHttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author MouFangCai
 * @date 2024/8/19 23:28
 * @description
 */
public interface ILoginService {

    public Boolean isLogin(HttpServletRequest request);


    public default void loginSuccess(HttpServletRequest request, HttpServletResponse response,Integer userId){
        LoginHttpUtil.setLoginCookie(new UserTokenDto(userId),request,response);
    }
}
