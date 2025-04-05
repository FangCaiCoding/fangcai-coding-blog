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
 * VIP兑换Token表
 * </p>
 *
 * @author MouFangCai
 * @since 2025-04-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("vip_token")
@Schema(description = "VIP兑换Token表")
public class VipToken extends BaseEntityWithDel {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "Token值")
    private String tokenValue;

    @Schema(description = "有效期")
    private LocalDateTime expireTime;

    @Schema(description = "可兑换次数")
    private Integer exchangeLimit;

    @Schema(description = "已兑换次数")
    private Integer exchangeCount;

    @Schema(description = "VIP有效期(天)")
    private Integer vipDays;

    @Schema(description = "是否有效")
    @TableField("is_valid")
    private Boolean valid;

    @Schema(description = "备注")
    private String remark;
}