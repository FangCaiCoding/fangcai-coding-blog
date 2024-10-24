package cn.fangcai.blog.model.res;

import com.baomidou.mybatisplus.annotation.TableField;
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

    // @Schema(description = "文章内容")
    // private String content;

    @Schema(description = "文章内容 markdown 格式")
    private String contentMd;


}
