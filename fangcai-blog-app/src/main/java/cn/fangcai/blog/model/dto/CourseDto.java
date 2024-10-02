package cn.fangcai.blog.model.dto;

import cn.fangcai.common.model.valider.EditGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author MouFangCai
 * @date 2024/9/16 21:44
 * @description
 */
@Data
@Schema(description = "教程信息 dto")
public class CourseDto {

    @Schema(description = "教程ID")
    @NotNull(message = "教程ID不能为空", groups = {EditGroup.class})
    private Integer id;

    @Schema(description = "教程标题")
    @NotNull(message = "教程标题不能为空")
    @Length(max = 128, message = "教程标题不能超过128个字符")
    private String title;

    @Schema(description = "教程摘要")
    @NotNull(message = "教程摘要不能为空")
    @Length(max = 300, message = "教程摘要不能超过300个字符")
    private String summary;

    @Schema(description = "教程头图")
    private String picture;

    @Schema(description = "状态：0-未发布，1-已发布")
    private Byte status;

    @Schema(description = "顺序号,升序排序")
    private Integer orderNum;


    @Schema(description = "用户ID", hidden = true)
    private Integer userId;
}
