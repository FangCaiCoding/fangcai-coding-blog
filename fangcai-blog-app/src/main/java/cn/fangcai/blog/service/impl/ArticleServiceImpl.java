package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.mapper.ArticleDetailMapper;
import cn.fangcai.blog.mapper.ArticleMapper;
import cn.fangcai.blog.mapstruct.ArticleConverter;
import cn.fangcai.blog.model.entity.Article;
import cn.fangcai.blog.model.entity.ArticleDetail;
import cn.fangcai.blog.model.entity.base.BaseEntity;
import cn.fangcai.blog.model.req.ArticlePageReq;
import cn.fangcai.blog.model.req.ArticleSaveReq;
import cn.fangcai.blog.model.res.ArticleDetailRes;
import cn.fangcai.blog.model.res.ArticleRes;
import cn.fangcai.blog.service.IArticleService;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.enums.FcErrorCodeEnum;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-08-25
 */
@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleDetailRepository articleDetailRepository;

    @Override
    public Integer addArticle(ArticleSaveReq saveReq) {
        Article article = ArticleConverter.INSTANCE.toArticle(saveReq);
        article.setId(null);
        articleRepository.save(article);
        articleDetailRepository.save(ArticleConverter.INSTANCE.toArticleDetail(article, saveReq));
        return article.getId();
    }

    @Override
    public Integer editArticle(ArticleSaveReq saveReq) {
        Article oldArticle = articleRepository.getById(saveReq.getId());
        if (oldArticle == null) {
            throw new FcBusinessException(FcErrorCodeEnum.BAD_REQUEST, "文章不存在");
        }
        // TODO : by mfc on 2024/8/25 暂时不做用户限制
        Article article = ArticleConverter.INSTANCE.toArticle(saveReq);
        articleRepository.updateById(article);
        articleDetailRepository.updateById(ArticleConverter.INSTANCE.toArticleDetail(article, saveReq));
        return article.getId();
    }

    @Override
    public ArticleDetailRes getDetail(Integer id) {
        Article article = articleRepository.getById(id);
        ArticleDetail articleDetail = articleDetailRepository.getById(id);
        return ArticleConverter.INSTANCE.toArticleDetailRes(article, articleDetail);
    }

    @Override
    public FcPageRes<ArticleRes> pageArticle(ArticlePageReq pageReq) {
        Page<Article> page = articleRepository.lambdaQuery()
                .like(StrUtil.isNotBlank(pageReq.getTitle()), Article::getTitle, pageReq.getTitle())
                .eq(!Objects.isNull(pageReq.getStatus()), Article::getStatus, pageReq.getStatus())
                .orderByAsc(Article::getOrderNum)
                .orderByDesc(BaseEntity::getCreateTime, Article::getId)
                .page(new Page<>(pageReq.getPage(), pageReq.getPageSize()));
        return new FcPageRes<ArticleRes>(pageReq)
                .total(page.getTotal())
                .records(ArticleConverter.INSTANCE.articleListToResList(page.getRecords()));
    }

    @Override
    public Boolean delArticle(Integer id) {
        articleRepository.removeById(id);
        return articleDetailRepository.removeById(id);
    }

    @Override
    public Boolean pinTopArticle(Integer id) {
        return null;
    }

    @Override
    public Boolean uptArticleStatus(Integer id, Boolean status) {
        return articleRepository.lambdaUpdate()
                .eq(Article::getId, id)
                .set(Article::getStatus, status)
                .update();
    }

    @Override
    public Boolean uptOrderNum(Integer id, Integer targetId) {
        return null;
    }


    @Component
    static class ArticleRepository extends ServiceImpl<ArticleMapper, Article> {

    }


    @Component
    static class ArticleDetailRepository extends ServiceImpl<ArticleDetailMapper, ArticleDetail> {

    }
}
