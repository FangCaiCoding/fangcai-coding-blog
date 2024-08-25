package cn.fangcai.blog.model.req;

import cn.fangcai.common.model.dto.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:39
 * @description
 */
@Schema(description = "文章分页请求")
@Data
public class ArticlePageReq extends PageReq {

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章状态")
    private Byte status;

}
