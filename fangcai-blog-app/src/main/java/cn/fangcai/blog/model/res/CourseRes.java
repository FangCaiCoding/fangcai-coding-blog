package cn.fangcai.blog.model.res;

import cn.fangcai.blog.model.dto.CourseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2024/9/16 21:30
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "教程响应对象")
@Data
public class CourseRes extends CourseDto {


    @Schema(description = "状态：0-未发布，1-已发布")
    private Byte status;

    @Schema(description = "顺序号,升序排序")
    private Integer orderNum;

    @Schema(description = "教程详情")
    private List<CourseDetailRes> details;


}