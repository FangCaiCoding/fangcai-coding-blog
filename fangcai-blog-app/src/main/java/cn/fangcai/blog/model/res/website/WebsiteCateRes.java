package cn.fangcai.blog.model.res.website;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 资源站点信息
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-18
 */
@Getter
@Setter
@Schema(description = "资源站点分类信息")
public class WebsiteCateRes {

    @Schema(description = "分类id")
    private Integer cateId;

    @Schema(description = "分类名称")
    private String cateName;

    @Schema(description = "序号")
    private Integer orderNum;

}
