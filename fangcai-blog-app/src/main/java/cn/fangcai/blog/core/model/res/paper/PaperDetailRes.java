package cn.fangcai.blog.core.model.res.paper;

import cn.fangcai.blog.core.model.dto.PaperDto;
import cn.fangcai.blog.core.model.dto.QuestionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
@Schema(description = "试卷详情 响应信息")
public class PaperDetailRes extends PaperDto {


    @Schema(description = "题目列表")
    private List<QuestionDto> questionList;

}
