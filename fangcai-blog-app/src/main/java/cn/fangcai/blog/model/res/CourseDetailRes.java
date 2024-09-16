package cn.fangcai.blog.model.res;

import cn.fangcai.common.model.valider.EditGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
    @NotNull(message = "主键ID不能为空", groups = {EditGroup.class})
    private Integer id;

    @Schema(description = "文章教程ID")
    @NotNull(message = "文章教程ID不能为空")
    private Integer courseId;

    @Schema(description = "文章ID")
    @NotNull(message = "文章ID不能为空")
    private Integer articleId;

    @Schema(description = "文章别名")
    @NotNull(message = "文章别名不能为空")
    private String articleAlias;

    @Schema(description = "顺序号,升序排序")
    private Integer orderNum;

}
