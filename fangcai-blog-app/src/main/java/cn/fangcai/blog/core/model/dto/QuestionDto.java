package cn.fangcai.blog.core.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Schema(description = "题目名称-别名（在每个试卷中，题目名称可不同")
    private String nameAlias;

    @Schema(description = "阅读数")
    private Integer readCt;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
