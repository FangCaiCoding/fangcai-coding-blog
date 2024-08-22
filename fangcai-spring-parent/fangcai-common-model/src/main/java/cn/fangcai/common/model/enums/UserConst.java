package cn.fangcai.common.model.enums;

/**
 * 系统用户相关常量
 *
 * @author MouFangCai
 * @date 2023/3/21 22:03
 * @description
 */
public interface UserConst {

    String PWD_REGEX = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[^a-zA-Z0-9\\s\\u4e00-\\u9fa5]).{8,}$";

    String PWD_ERROR_MSG = "密码中必须包含大(小)写字母、数字、特殊字符(不含中文和空格)，且长度不少于8位";

    String LOGIN_NAME_REGEX = "^[^\\u4e00-\\u9fa5]+$";

    String LOGIN_NAME_ERROR_MSG = "账户由字母、数字或特殊字符组成";

}
