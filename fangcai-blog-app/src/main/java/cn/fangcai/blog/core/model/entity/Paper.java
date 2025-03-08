package cn.fangcai.blog.core.model.entity;

import cn.fangcai.blog.core.model.entity.base.BaseEntityWithDel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 试卷表
 * </p>
 *
 * @author MouFangCai
 * @since 2025-03-08
 */
@Getter
@Setter
@TableName("paper")
@Schema(name = "Paper", description = "试卷表")
public class Paper extends BaseEntityWithDel {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "试卷分类ID")
    @TableField("paper_cate_id")
    private Integer paperCateId;

    @Schema(description = "试卷名称")
    @TableField("name")
    private String name;

    @Schema(description = "顺序号,升序排序")
    @TableField("order_num")
    private Integer orderNum;

    @Schema(description = "题目总数")
    @TableField("question_total")
    private Integer questionTotal;


    @Schema(description = "阅读数")
    private Integer readCt;

    @Schema(description = "状态：0-未发布，1-已发布")
    private Byte status;

}
