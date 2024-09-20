package cn.fangcai.blog.service;

import cn.fangcai.blog.model.req.ArticlePageReq;
import cn.fangcai.blog.model.req.ArticleSaveReq;
import cn.fangcai.blog.model.res.ArticleDetailRes;
import cn.fangcai.blog.model.res.ArticleRes;
import cn.fangcai.common.model.dto.FcPageRes;

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

    Integer editArticle(ArticleSaveReq saveReq);

    ArticleDetailRes getDetail(Integer id);

    Boolean incrReadCt(Integer id);

    FcPageRes<ArticleRes> pageArticle(ArticlePageReq pageReq);

    Boolean delArticle(Integer id);

    Boolean pinTopArticle(Integer id);

    Boolean uptArticleStatus(Integer id, Boolean status);

    Boolean uptOrderNum(Integer id, Integer targetId);
}
