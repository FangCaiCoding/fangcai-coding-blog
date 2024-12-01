package cn.fangcai.blog.mapstruct;

import cn.fangcai.blog.core.model.entity.Article;
import cn.fangcai.blog.core.model.entity.ArticleDetail;
import cn.fangcai.blog.core.model.entity.ArticleTemplate;
import cn.fangcai.blog.core.model.req.ArticleSaveReq;
import cn.fangcai.blog.core.model.res.ArticleDetailRes;
import cn.fangcai.blog.core.model.res.ArticleRes;
import cn.fangcai.blog.core.model.res.ArticleTemplateRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:50
 * @description
 */
@Mapper
public interface ArticleConverter {
    ArticleConverter INSTANCE = Mappers.getMapper(ArticleConverter.class);


    Article toArticle(ArticleSaveReq saveReq);

    @Mapping(target = "articleId", source = "article.id")
    ArticleDetail toArticleDetail(Article article, ArticleSaveReq saveReq);

    @Mapping(target = "createTime", source = "articleDetail.createTime")
    @Mapping(target = "updateTime", source = "articleDetail.updateTime")
    ArticleDetailRes toArticleDetailRes(Article article, ArticleDetail articleDetail);

    List<ArticleRes> articleListToResList(List<Article> records);

    List<ArticleTemplateRes> templateListToResList(List<ArticleTemplate> records);
}
