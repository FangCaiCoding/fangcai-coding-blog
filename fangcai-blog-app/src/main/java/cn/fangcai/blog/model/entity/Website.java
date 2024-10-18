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
 * 资源站点信息
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-18
 */
@Getter
@Setter
@TableName("website")
@Schema(name = "Website", description = "资源站点信息")
public class Website extends BaseEntity {


    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "分类id")
    @TableField("cate_id")
    private Integer cateId;

    @Schema(description = "标题")
    @TableField("title")
    private String title;

    @Schema(description = "网址")
    @TableField("web_url")
    private String webUrl;

    @Schema(description = "头图")
    @TableField("picture")
    private String picture;

    @Schema(description = "作者")
    @TableField("author")
    private String author;

    @Schema(description = "简介")
    @TableField("intro")
    private String intro;

    @Schema(description = "亮点")
    @TableField("bright_spot")
    private String brightSpot;

    @Schema(description = "阅读数")
    @TableField("read_ct")
    private Integer readCt;

    @Schema(description = "状态：0-未发布，1-已发布")
    @TableField("status")
    private Byte status;

    @Schema(description = "顺序号,升序排序")
    @TableField("order_num")
    private Integer orderNum;

}
