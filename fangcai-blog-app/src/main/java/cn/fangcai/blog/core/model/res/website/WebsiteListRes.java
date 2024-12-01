package cn.fangcai.blog.core.model.res.website;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
@Schema(description = "资源站点列表信息")
public class WebsiteListRes {

    @Schema(description = "分类id")
    private Integer cateId;

    @Schema(description = "分类名称")
    private String cateName;

    @Schema(description = "站点列表")
    private List<WebsiteRes> websiteList;
}
