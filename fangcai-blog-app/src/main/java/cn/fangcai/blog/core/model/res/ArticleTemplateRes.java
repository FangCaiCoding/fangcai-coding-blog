package cn.fangcai.blog.core.model.res;

import cn.fangcai.blog.core.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章通用内容模板表
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-22
 */
@Getter
@Setter
@Schema(name = "ArticleTemplate", description = "文章通用内容模板表")
public class ArticleTemplateRes extends BaseEntity {


    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "头 文章内容 markdown 格式")
    private String headerContentMd;

    @Schema(description = "尾  文章内容 markdown 格式")
    private String footerContentMd;


}
