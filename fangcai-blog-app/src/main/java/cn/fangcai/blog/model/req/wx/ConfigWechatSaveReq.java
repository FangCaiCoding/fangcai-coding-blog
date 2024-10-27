package cn.fangcai.blog.model.req.wx;

import cn.fangcai.common.model.valider.EditGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 微信配置类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-27
 */
@Getter
@Setter
@Schema(name = "ConfigWechat", description = "微信配置类")
public class ConfigWechatSaveReq {

    @NotNull(message = "文章ID不能为空", groups = {EditGroup.class})
    private Integer id;

    @Schema(description = "名称")
    @NotBlank(message = "名称")
    @Length(max = 64, message = "名称 不能超过64个字符")
    private String name;

    @Schema(description = "关键字")
    @NotBlank(message = "关键字")
    @Length(max = 64, message = "关键字 不能超过64个字符")
    private String key;

    @Schema(description = "信息（微信后台限制就是 600）")
    @NotBlank(message = "信息")
    @Length(max = 600, message = "信息 不能超过600个字符")
    private String value;

    @Schema(description = "是否启用 1-启用 0-停用")
    private Byte isEnabled;

    @Schema(description = "顺序号,升序排序")
    private Integer orderNum;

}
