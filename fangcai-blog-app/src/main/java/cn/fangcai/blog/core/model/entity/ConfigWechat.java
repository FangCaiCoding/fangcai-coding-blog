package cn.fangcai.blog.core.model.entity;

import cn.fangcai.blog.core.model.entity.base.BaseEntityWithDel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 微信配置类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-27
 */
@Getter
@Setter
@TableName("config_wechat")
@Schema(name = "ConfigWechat", description = "微信配置类")
public class ConfigWechat extends BaseEntityWithDel {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "消息类型 1-文本 2-图片")
    @TableField("msg_type")
    private Byte msgType;

    @Schema(description = "关键字")
    @TableField("key_str")
    private String keyStr;

    @Schema(description = "信息（微信后台限制就是600）")
    @TableField("msg_value")
    private String msgValue;

    @Schema(description = "是否启用 1-启用 0-停用")
    @TableField("is_enabled")
    private Boolean enabled;

    @Schema(description = "顺序号,升序排序")
    @TableField("order_num")
    private Integer orderNum;

}
