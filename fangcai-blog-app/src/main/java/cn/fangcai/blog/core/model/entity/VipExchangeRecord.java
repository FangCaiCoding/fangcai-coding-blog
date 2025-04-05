package cn.fangcai.blog.core.model.entity;

import cn.fangcai.blog.core.model.entity.base.BaseEntity;
import cn.fangcai.blog.core.model.entity.base.BaseEntityWithDel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * VIP兑换记录表
 * </p>
 *
 * @author MouFangCai
 * @since 2025-04-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("vip_exchange_record")
@Schema(description = "VIP变更记录表")
public class VipExchangeRecord extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "Token ID")
    private Integer tokenId;

    @Schema(description = "原VIP结束时间")
    private LocalDateTime oldVipEndTime;

    @Schema(description = "新VIP结束时间")
    private LocalDateTime vipEndTime;

    @Schema(description = "备注")
    private String remark;
}