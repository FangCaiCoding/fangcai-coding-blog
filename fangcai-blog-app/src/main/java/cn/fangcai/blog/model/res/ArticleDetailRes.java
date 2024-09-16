package cn.fangcai.blog.model.res;

import cn.fangcai.common.model.valider.EditGroup;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:23
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文章详情")
@Data
public class ArticleDetailRes extends ArticleRes {


    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "文章内容 markdown 格式")
    private String contentMd;


}
