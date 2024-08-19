package fc.common.auth;

import fc.common.auth.dto.UserContextDto;
import fc.common.model.common.exception.FcException;
import fc.common.model.enums.FcErrorCodeEnum;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:16
 * @description
 */
public class UserContext extends ThreadLocal<UserContextDto> {
    private static final ThreadLocal<UserContextDto> USER_INFO = new ThreadLocal<UserContextDto>();


    public static void initContext(UserContextDto userInfo) {
        USER_INFO.set(userInfo);
    }

    public static void clearContext() {
        USER_INFO.remove();
    }

    public static UserContextDto getUserInfo() {
        UserContextDto dto = USER_INFO.get();
        if (dto == null) {
            throw new FcException(FcErrorCodeEnum.USER_CONTEXT_IS_NULL);
        }
        return dto;
    }

    public static Integer getUserId() {
        return getUserInfo().getId();
    }



}
