package cn.fangcai.blog.core.model.entity.base;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author MouFangCai
 * @date 2023/3/23 23:11
 * @description
 */
@Data
@Schema(description = "基础的entity")
public class BaseEntity  {

    @Schema(description = "创建时间")
    @TableField(value = "create_time",insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time",insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;
}
