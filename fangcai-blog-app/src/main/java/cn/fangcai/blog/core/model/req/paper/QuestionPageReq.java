package cn.fangcai.blog.core.model.req.paper;

import cn.fangcai.common.model.dto.PageReq;
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
@Schema(name = "QuestionPageReq", description = "题目 分页查询条件")
public class QuestionPageReq extends PageReq {

    @Schema(description = "题目名称")
    private String name;

}
