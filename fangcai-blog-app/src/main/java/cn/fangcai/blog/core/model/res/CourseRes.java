package cn.fangcai.blog.core.model.res;

import cn.fangcai.blog.core.model.dto.CourseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
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


    @Schema(description = "阅读数")
    private Integer readCt;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Schema(description = "教程详情")
    private List<CourseDetailRes> details;


}
