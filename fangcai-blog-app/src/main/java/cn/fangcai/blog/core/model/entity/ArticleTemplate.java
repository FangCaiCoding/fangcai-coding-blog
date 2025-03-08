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
public class ArticleTemplate extends BaseEntityWithDel {


    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "标题")
    @TableField("title")
    private String title;

    @Schema(description = "头 文章内容 markdown 格式")
    @TableField("header_content_md")
    private String headerContentMd;

    @Schema(description = "尾  文章内容 markdown 格式")
    @TableField("footer_content_md")
    private String footerContentMd;


}
