package cn.fangcai.blog.model.entity.base;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MouFangCai
 * @date 2023/3/23 23:11
 * @description
 */
@Data
@Schema(description = "基础的entity")
public class BaseEntityWithUk {

    @Schema(description = "删除状态，0未删除，其他-表示已删除")
    @TableField(value = "is_uk_deleted")
    // @TableLogic(value = "0", delval = "#{cn.hutool.core.util.IdUtil.getSnowflakeNextId();}")
    @TableLogic(value = "0", delval = "Round(UNIX_TIMESTAMP(NOW(3)) * 1000)")
    private Long ukDeleted;


    @Schema(description = "创建时间")
    @TableField(value = "create_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;
}
