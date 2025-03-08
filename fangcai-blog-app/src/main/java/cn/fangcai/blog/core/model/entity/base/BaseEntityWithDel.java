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
@Schema(description = "基础的entity,包含删除状态字段")
public class BaseEntityWithDel extends BaseEntity {

    @Schema(description = "删除状态，0未删除，1删除")
    @TableField(value = "is_deleted")
    @TableLogic(value = "0", delval = "1")
    private Boolean deleted;

}
