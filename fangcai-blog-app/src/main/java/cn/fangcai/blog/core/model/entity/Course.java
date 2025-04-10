package cn.fangcai.blog.core.model.entity;

import cn.fangcai.blog.core.model.entity.base.BaseEntityWithDel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章教程
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-16
 */
@Getter
@Setter
@TableName("course")
@Schema(name = "Course", description = "文章教程")
public class Course extends BaseEntityWithDel {

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "教程名")
    @TableField("title")
    private String title;

    @Schema(description = "教程视频链接")
    @TableField("video_url")
    private String videoUrl;


    @Schema(description = "教程头图")
    @TableField("picture")
    private String picture;

    @Schema(description = "教程摘要")
    @TableField("summary")
    private String summary;

    @Schema(description = "状态：0-未发布，1-已发布")
    @TableField("status")
    private Byte status;

    @Schema(description = "顺序号,升序排序")
    @TableField("order_num")
    private Integer orderNum;

    @Schema(description = "阅读数")
    @TableField("read_ct")
    private Integer readCt;

}
