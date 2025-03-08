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
 * 题目表
 * </p>
 *
 * @author MouFangCai
 * @since 2025-03-08
 */
@Getter
@Setter
@TableName("question")
@Schema(name = "Question", description = "题目表")
public class Question extends BaseEntityWithDel {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "题目名称")
    @TableField("name")
    private String name;

    @Schema(description = "题目介绍")
    @TableField("intro")
    private String intro;

    @Schema(description = "答案")
    @TableField("answer")
    private String answer;

    @Schema(description = "解析")
    @TableField("analysis")
    private String analysis;

    @Schema(description = "阅读数")
    @TableField("read_ct")
    private Integer readCt;


}
