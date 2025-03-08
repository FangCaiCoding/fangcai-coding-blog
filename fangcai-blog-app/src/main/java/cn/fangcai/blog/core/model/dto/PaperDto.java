package cn.fangcai.blog.core.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 试卷表
 * </p>
 *
 * @author MouFangCai
 * @since 2025-03-08
 */
@Getter
@Setter
@Schema(description = "题库 dto")
public class PaperDto {

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "试卷分类ID")
    private Integer paperCateId;

    @Schema(description = "试卷分类 名称")
    private String paperCateName;

    @Schema(description = "试卷名称")
    private String name;

    @Schema(description = "顺序号,升序排序")
    private Integer orderNum;

    @Schema(description = "题目总数")
    private Integer questionTotal;

    @Schema(description = "阅读数")
    private Integer readCt;

    @Schema(description = "状态：0-未发布，1-已发布")
    private Byte status;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}
