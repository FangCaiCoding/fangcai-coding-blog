package cn.fangcai.blog.model.req;

import cn.fangcai.blog.model.dto.CourseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MouFangCai
 * @date 2024/9/16 21:30
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "教程教程 保存请求")
@Data
public class CourseSaveReq extends CourseDto {


}
