package cn.fangcai.blog.core.model.req.paper;

import cn.fangcai.common.model.valider.EditGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "QuestionUptReq", description = "题目 修改请求参数")
public class QuestionUptReq {

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Integer id;

    @Schema(description = "题目名称")
    private String name;

    @Schema(description = "题目介绍")
    private String intro;

    @Schema(description = "答案")
    private String answer;

    @Schema(description = "解析")
    private String analysis;

}
