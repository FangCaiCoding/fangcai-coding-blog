package cn.fangcai.blog.core.model.entity;

import cn.fangcai.blog.core.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章详情表
 * </p>
 *
 * @author MouFangCai
 * @since 2024-08-25
 */
@Getter
@Setter
@TableName("article_detail")
@Schema(name = "ArticleDetail", description = "文章详情表")
public class ArticleDetail extends BaseEntity {


    @Schema(description = "文章ID")
    @TableId(value = "article_id", type = IdType.INPUT)
    private Integer articleId;



    @Schema(description = "文章内容 markdown 格式")
    @TableField("content_md")
    private String contentMd;

    @Schema(description = "文章内容")
    @TableField("content")
    private String content;

}
