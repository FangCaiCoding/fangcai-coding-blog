package cn.fangcai.blog.core.model.entity;

import cn.fangcai.blog.core.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色-菜单关系表
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-26
 */
@Getter
@Setter
@TableName("role_menu")
@Schema(name = "RoleMenu", description = "角色-菜单关系表")
public class RoleMenu extends BaseEntity {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "角色 id")
    @TableField("role_id")
    private Integer roleId;

    @Schema(description = "菜单 id")
    @TableField("menu_id")
    private Integer menuId;

    @Schema(description = "操作者-用户ID")
    @TableField("operator")
    private Integer operator;

}
