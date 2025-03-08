package cn.fangcai.blog.core.model.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author MouFangCai
 * @date 2023/3/23 23:11
 * @description
 */
@Data
@Schema(description = "基础的entity")
public class BaseEntityWithUk extends BaseEntity {

    @Schema(description = "删除状态，0未删除，其他-表示已删除")
    @TableField(value = "is_uk_deleted")
    @TableLogic(value = "0", delval = "Round(UNIX_TIMESTAMP(NOW(3)) * 1000)")
    private Long ukDeleted;

}
