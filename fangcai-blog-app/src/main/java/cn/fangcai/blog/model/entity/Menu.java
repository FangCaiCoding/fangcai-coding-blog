package cn.fangcai.blog.model.entity;

import cn.fangcai.blog.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单（权限）列表
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-25
 */
@Getter
@Setter
@TableName("menu")
@Schema(name = "Menu", description = "菜单（权限）列表")
public class Menu extends BaseEntity {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "菜单名")
    @TableField("name")
    private String name;

    @Schema(description = "菜单类型 1-目录  2-页面  3-按钮")
    @TableField("menu_type")
    private Byte menuType;

    @Schema(description = "顺序号,升序排序")
    @TableField("order_num")
    private Integer orderNum;

    @Schema(description = "路由地址")
    @TableField("route_key")
    private String routeKey;

    @Schema(description = "权限码")
    @TableField("auth_code")
    private String authCode;

    @Schema(description = "接口权限码集合")
    @TableField("api_code_list")
    private String apiCodeList;

    @Schema(description = "是否启用 1-启用 0-停用")
    @TableField("is_enabled")
    private Byte isEnabled;

    @Schema(description = "操作者-用户ID")
    @TableField("operator")
    private Integer operator;
}
