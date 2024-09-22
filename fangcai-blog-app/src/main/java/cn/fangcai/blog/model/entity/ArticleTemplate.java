package cn.fangcai.blog.model.entity;

import cn.fangcai.blog.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章通用内容模板表
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-22
 */
@Getter
@Setter
@TableName("article_template")
@Schema(name = "ArticleTemplate", description = "文章通用内容模板表")
public class ArticleTemplate extends BaseEntity {


    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "标题")
    @TableField("title")
    private String title;

    @Schema(description = "文章内容 markdown 格式")
    @TableField("content_md")
    private String contentMd;

    @Schema(description = "文章内容")
    @TableField("content")
    private String content;

}
