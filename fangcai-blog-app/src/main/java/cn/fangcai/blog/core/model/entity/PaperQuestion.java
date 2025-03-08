package cn.fangcai.blog.core.model.entity;

import cn.fangcai.blog.core.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 试卷题目关系表
 * </p>
 *
 * @author MouFangCai
 * @since 2025-03-08
 */
@Getter
@Setter
@TableName("paper_question")
@Schema(name = "PaperQuestion", description = "试卷题目关系表")
public class PaperQuestion extends BaseEntity {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "试卷ID")
    @TableField("paper_id")
    private Integer paperId;

    @Schema(description = "题目ID")
    @TableField("question_id")
    private Integer questionId;

    @Schema(description = "题目别名")
    @TableField("question_alias")
    private String questionAlias;

    @Schema(description = "顺序号,升序排序")
    @TableField("order_num")
    private Integer orderNum;


}
