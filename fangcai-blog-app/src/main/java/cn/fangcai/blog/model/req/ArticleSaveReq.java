package cn.fangcai.blog.model.req;

import cn.fangcai.common.model.valider.EditGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:23
 * @description
 */
@Schema(description = "文章保存请求")
@Data
public class ArticleSaveReq {

    @Schema(description = "文章ID")
    @NotNull(message = "文章ID不能为空", groups = {EditGroup.class})
    private Integer id;

    @Schema(description = "文章标题")
    @Length(max = 128, message = "文章标题不能超过128个字符")
    private String title;

    @Schema(description = "文章摘要")
    @Length(max = 300, message = "文章摘要不能超过300个字符")
    private String summary;

    @Schema(description = "文章头图")
    private String picture;

    @Schema(description = "文章内容")
    @Length(max = 100000, message = "文章内容不能超过100000个字符")
    private String content;

    @Schema(description = "文章内容 markdown 格式")
    @NotEmpty(message = "文章内容不能为空")
    @Length(max = 100000, message = "文章内容不能超过100000个字符")
    private String contentMd;


    @Schema(description = "用户ID", hidden = true)
    private Integer userId;

}