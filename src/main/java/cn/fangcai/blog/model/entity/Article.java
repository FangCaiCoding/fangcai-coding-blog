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
 * 文章表
 * </p>
 *
 * @author MouFangCai
 * @since 2024-08-25
 */
@Getter
@Setter
@TableName("article")
@Schema(name = "Article", description = "文章表")
public class Article extends BaseEntity {

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "文章标题")
    @TableField("title")
    private String title;

    @Schema(description = "文章头图")
    @TableField("picture")
    private String picture;

    @Schema(description = "文章摘要")
    @TableField("summary")
    private String summary;

    @Schema(description = "状态：0-未发布，1-已发布")
    @TableField("status")
    private Byte status;

    @Schema(description = "顺序号,降序排序")
    @TableField("order_num")
    private Integer orderNum;

}