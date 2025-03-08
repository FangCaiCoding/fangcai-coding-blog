package cn.fangcai.blog.core.model.res.paper;

import cn.fangcai.blog.core.model.dto.PaperDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author MouFangCai
 * @since 2024-10-18
 */
@Getter
@Setter
@Schema(description = "题库列表信息")
public class PaperListRes {

    @Schema(description = "分类id")
    private Integer cateId;

    @Schema(description = "分类名称")
    private String cateName;

    @Schema(description = "题库列表")
    private List<PaperDto> paperList;
}
