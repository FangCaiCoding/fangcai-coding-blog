package cn.fangcai.blog.core.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:23
 * @description
 */
@Schema(description = "文章基础信息")
@Data
public class ArticleRes {

    @Schema(description = "文章ID")
    private Integer id;

    @Schema(description = "教程id，若有多个，则随机返回一个")
    private Integer courseId;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "文章头图")
    private String picture;

    @Schema(description = " 模板id")
    private Integer templateId;

    @Schema(description = "可直接阅读的限制比例 百分制，按行数计算")
    private Byte readLimitRatio;

    @Schema(description = "阅读限制类型：0-不限制 1-需登录 2-需VIP")
    private Byte limitType;

    @Schema(description = "状态：0-未发布，1-已发布")
    private Byte status;

    @Schema(description = "阅读数")
    private Integer readCt;

    @Schema(description = "顺序号,升序排序")
    private Integer orderNum;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
