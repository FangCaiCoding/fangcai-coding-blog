package fc.common.auth.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author MouFangCai
 * @date 2024/8/19 23:28
 * @description
 */
public interface ILoginService {

    public Boolean isLogin(HttpServletRequest request);
}
