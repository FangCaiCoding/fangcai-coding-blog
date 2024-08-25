package cn.fangcai.blog.model.res;

import cn.fangcai.common.model.valider.EditGroup;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:23
 * @description
 */
@Schema(description = "文章详情")
@Data
public class ArticleDetailRes {

    @Schema(description = "文章ID")
    private Integer id;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "状态：0-未发布，1-已发布")
    private Byte status;

    @Schema(description = "文章头图")
    private String picture;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "文章内容 markdown 格式")
    private String contentMd;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
