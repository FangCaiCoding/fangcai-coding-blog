package cn.fangcai.blog.core.model.entity;

import cn.fangcai.blog.core.model.entity.base.BaseEntityWithDel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author MouFangCai
 * @since 2023-03-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "user", autoResultMap = true)
@Schema(description = "用户表")
public class User extends BaseEntityWithDel {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "微信openid")
    private String wxOpenId;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "头像文字（显示优先级低于头像）")
    private String avatarStr;

    @Schema(description = "密码")
    private String password;

    @TableField(value = "is_enabled")
    @Schema(description = "是否启用 1-启用 0-停用")
    private Boolean enabled;


    @Schema(description = "vip 到期时间")
    @TableField(value = "vip_end_time")
    private LocalDateTime vipEndTime;

}
