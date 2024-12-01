package cn.fangcai.blog.core.model.res;

import cn.fangcai.common.model.valider.EditGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章教程-详情
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-16
 */
@Getter
@Setter
@Schema( description = "文章教程-详情")
public class CourseDetailRes {

    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "文章教程ID")
    private Integer courseId;

    @Schema(description = "文章ID")
    private Integer articleId;

    @Schema(description = "文章标题")
    private String articleTitle;

    @Schema(description = "文章别名")
    private String articleAlias;

    @Schema(description = "顺序号,升序排序")
    private Integer orderNum;

    @Schema(description = "添加时间")
    private LocalDateTime createTime;

}
