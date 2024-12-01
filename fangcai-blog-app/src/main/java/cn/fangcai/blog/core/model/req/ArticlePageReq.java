package cn.fangcai.blog.core.model.req;

import cn.fangcai.common.model.dto.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:39
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文章分页请求")
@Data
public class ArticlePageReq extends PageReq {

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章状态")
    private Byte status;

    @Schema(description = "期望排除的文章教程ID")
    private Integer excludeCourseId;

}
