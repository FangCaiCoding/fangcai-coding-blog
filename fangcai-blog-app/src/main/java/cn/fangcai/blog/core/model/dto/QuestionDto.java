package cn.fangcai.blog.core.model.dto;

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
@Schema(name = "Question", description = "题目表")
public class QuestionDto {

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "题目名称")
    private String name;

    @Schema(description = "阅读数")
    private Integer readCt;


}
