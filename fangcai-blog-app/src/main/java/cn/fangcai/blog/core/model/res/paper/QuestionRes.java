package cn.fangcai.blog.core.model.res.paper;

import cn.fangcai.blog.core.model.dto.QuestionDto;
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
public class QuestionRes extends QuestionDto {

    @Schema(description = "题目介绍")
    private String intro;

    @Schema(description = "答案")
    private String answer;

    @Schema(description = "解析")
    private String analysis;

}
