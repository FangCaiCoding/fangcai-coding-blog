package cn.fangcai.blog.core.model.res.website;

import cn.fangcai.blog.core.model.entity.base.BaseEntityWithDel;
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
@Schema(description = "资源站点信息")
public class WebsiteRes extends BaseEntityWithDel {

    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "用户ID",hidden = true)
    private Integer userId;

    @Schema(description = "分类id")
    @NotNull(message = "分类 不能为空")
    private Integer cateId;

    @Schema(description = "分类名称")
    private String cateName;

    @Schema(description = "标题")
    @NotBlank(message = "标题 不能为空")
    @Length(max = 128, message = "标题不能超过128个字符")
    private String title;

    @Schema(description = "网址")
    private String webUrl;

    @Schema(description = "站点图片")
    @NotBlank(message = "站点图片 不能为空")
    @Length(max = 255, message = "站点图片 不能超过255个字符")
    private String picture;

    @Schema(description = "作者")
    @NotBlank(message = "作者 不能为空")
    private String author;

    @Schema(description = "作者简介")
    private String authorIntro;

    @Schema(description = "简介")
    @NotBlank(message = "简介 不能为空")
    @Length(max = 300, message = "简介 不能超过300个字符")
    private String intro;

    @Schema(description = "亮点")
    @Length(max = 300, message = "亮点 不能超过300个字符")
    private String brightSpot;

    @Schema(description = "阅读数")
    private Integer readCt;

    @Schema(description = "状态：0-未发布，1-已发布")
    private Byte status = 0;

    @Schema(description = "顺序号,升序排序")
    private Integer orderNum = 99;

}
