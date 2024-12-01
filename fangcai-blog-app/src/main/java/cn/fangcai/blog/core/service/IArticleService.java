package cn.fangcai.blog.core.service;

import cn.fangcai.blog.core.model.res.ArticleDetailRes;
import cn.fangcai.blog.core.model.res.ArticleRes;
import cn.fangcai.blog.core.model.res.ArticleTemplateRes;
import cn.fangcai.blog.core.model.req.ArticlePageReq;
import cn.fangcai.blog.core.model.req.ArticleSaveReq;
import cn.fangcai.blog.core.model.req.ArticleTemplatePageReq;
import cn.fangcai.common.model.dto.FcPageRes;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-08-25
 */
public interface IArticleService {

    Integer addArticle(ArticleSaveReq saveReq);

    Integer editArticle(ArticleSaveReq editReq);

    ArticleDetailRes getDetail(Integer id, Boolean needTemplate);

    Boolean incrReadCt(Integer id);

    FcPageRes<ArticleRes> pageArticle(ArticlePageReq pageReq);

    Boolean delArticle(Integer id);

    Boolean uptArticleStatus(Integer id, Boolean status);
    Boolean initOrderNum();


    List<ArticleRes> listByIds(List<Integer> articleIdList);


    FcPageRes<ArticleTemplateRes> pageArticleTemplate(ArticleTemplatePageReq pageReq);
}
