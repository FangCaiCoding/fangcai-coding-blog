package cn.fangcai.blog.core.model.res.paper;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author MouFangCai
 * @since 2024-10-18
 */
@Getter
@Setter
@Schema(description = "题库分类信息")
public class PaperCateRes {

    @Schema(description = "分类id")
    private Integer cateId;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "序号")
    private Integer orderNum;

}
