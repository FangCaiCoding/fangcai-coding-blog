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

    @Schema(description = "文章状态 0-未发布 1-已发布")
    private Byte status;

    @Schema(description = "限制类型 0-不限制 1-需登录 2-需VIP")
    private Byte limitType;

    @Schema(description = "排序字段 ArticleOrderByFiledEnum 枚举值 0-默认排序 1-浏览量  2-发布时间")
    private Byte orderField;

    @Schema(description = "升序排列 true-升序 false-降序（默认值） ")
    private Boolean isAsc = false;

    @Schema(description = "期望排除的文章教程ID")
    private Integer excludeCourseId;

}
