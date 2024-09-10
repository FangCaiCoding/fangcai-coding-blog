package cn.fangcai.blog.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:23
 * @description
 */
@Schema(description = "文章基础信息")
@Data
public class ArticleRes {

    @Schema(description = "文章ID")
    private Integer id;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "文章头图")
    private String picture;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
