package cn.fangcai.blog.model.entity;

import cn.fangcai.blog.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Schema(description = "用户表")
public class User extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "密码")
    private String password;

    @TableField(value = "is_enabled")
    @Schema(description = "是否启用 1-启用 0-停用")
    private Boolean enabled;


}
