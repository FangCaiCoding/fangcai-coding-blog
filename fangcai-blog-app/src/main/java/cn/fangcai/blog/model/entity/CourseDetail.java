package cn.fangcai.blog.model.entity;

import cn.fangcai.blog.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章教程-详情
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-16
 */
@Getter
@Setter
@TableName("course_detail")
@Schema(name = "CourseDetail", description = "文章教程-详情")
public class CourseDetail extends BaseEntity {


    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "文章教程ID")
    @TableField("course_id")
    private Integer courseId;

    @Schema(description = "文章ID")
    @TableField("article_id")
    private Integer articleId;

    @Schema(description = "文章别名")
    @TableField("article_alias")
    private String articleAlias;

    @Schema(description = "顺序号,升序排序")
    @TableField("order_num")
    private Integer orderNum;

}
