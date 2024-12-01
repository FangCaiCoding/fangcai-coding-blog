package cn.fangcai.blog.core.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:23
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文章详情")
@Data
public class ArticleDetailRes extends ArticleRes {

    @Schema(description = " 模板id")
    private Integer templateId;

    // @Schema(description = "文章内容-html格式")
    // private String content;

    @Schema(description = "文章内容 markdown 格式")
    private String contentMd;

    @Schema(description = "内容是否结束")
    private Boolean contendIsEnd = true;
}
