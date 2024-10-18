package cn.fangcai.blog.model.req.website;

import cn.fangcai.common.model.valider.EditGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 资源站点信息
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-18
 */
@Getter
@Setter
@Schema(name = "Website", description = "资源站点信息")
public class WebsiteSaveReq {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID", groups = {EditGroup.class})
    private Integer id;

    @Schema(description = "用户ID",hidden = true)
    private Integer userId;

    @Schema(description = "分类id")
    @NotNull(message = "分类 不能为空")
    private Integer cateId;

    @Schema(description = "标题")
    @NotBlank(message = "标题 不能为空")
    @Length(max = 128, message = "标题不能超过128个字符")
    private String title;

    @Schema(description = "站点图片")
    @NotBlank(message = "站点图片 不能为空")
    @Length(max = 255, message = "站点图片 不能超过255个字符")
    private String picture;

    @Schema(description = "作者")
    @NotBlank(message = "作者 不能为空")
    @Length(max = 64, message = "作者 不能超过64个字符")
    private String author;

    @Schema(description = "简介")
    @NotBlank(message = "简介 不能为空")
    @Length(max = 300, message = "简介 不能超过300个字符")
    private String intro;

    @Schema(description = "亮点")
    @Length(max = 300, message = "亮点 不能超过300个字符")
    private String brightSpot;

    @Schema(description = "状态：0-未发布，1-已发布")
    private Byte status = 0;

    @Schema(description = "顺序号,升序排序")
    private Integer orderNum = 99;

}
