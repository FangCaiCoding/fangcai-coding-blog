package cn.fangcai.blog.model.entity;

import cn.fangcai.blog.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户-角色关系表
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-26
 */
@Getter
@Setter
@TableName("user_role")
@Schema(name = "UserRole", description = "用户-角色关系表")
public class UserRole extends BaseEntity {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户 id")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "角色 id")
    @TableField("role_id")
    private Integer roleId;

    @Schema(description = "操作者-用户ID")
    @TableField("operator")
    private Integer operator;

}
