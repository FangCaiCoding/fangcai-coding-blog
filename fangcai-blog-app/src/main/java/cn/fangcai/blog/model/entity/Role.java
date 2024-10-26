package cn.fangcai.blog.model.entity;

import cn.fangcai.blog.model.entity.base.BaseEntityWithUk;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色信息（带角色权限关系）
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-25
 */
@Getter
@Setter
@TableName(value = "role", autoResultMap = true)
@Schema(name = "Role", description = "角色信息（带角色权限关系）")
public class Role extends BaseEntityWithUk {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "角色名")
    @TableField("name")
    private String name;

    @Schema(description = "顺序号,升序排序")
    @TableField("order_num")
    private Integer orderNum;

    @Schema(description = "是否启用 1-启用 0-停用")
    @TableField("is_enabled")
    private Boolean enabled;

    @Schema(description = "操作者-用户ID")
    @TableField("operator")
    private Integer operator;


}
