package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.mapper.ArticleDetailMapper;
import cn.fangcai.blog.mapper.ArticleMapper;
import cn.fangcai.blog.mapper.ArticleTemplateMapper;
import cn.fangcai.blog.mapstruct.ArticleConverter;
import cn.fangcai.blog.model.entity.Article;
import cn.fangcai.blog.model.entity.ArticleDetail;
import cn.fangcai.blog.model.entity.ArticleTemplate;
import cn.fangcai.blog.model.entity.base.BaseEntity;
import cn.fangcai.blog.model.req.ArticlePageReq;
import cn.fangcai.blog.model.req.ArticleSaveReq;
import cn.fangcai.blog.model.req.ArticleTemplatePageReq;
import cn.fangcai.blog.model.res.ArticleDetailRes;
import cn.fangcai.blog.model.res.ArticleRes;
import cn.fangcai.blog.model.res.ArticleTemplateRes;
import cn.fangcai.blog.service.IArticleService;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.enums.FcErrorCodeEnum;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private ArticleTemplateRepository teamRepository;

    @Override
    public Integer addArticle(ArticleSaveReq saveReq) {
        Article article = ArticleConverter.INSTANCE.toArticle(saveReq);
        article.setId(null);
        articleRepository.save(article);
        articleDetailRepository.save(ArticleConverter.INSTANCE.toArticleDetail(article, saveReq));
        return article.getId();
    }

    @Override
    public Integer editArticle(ArticleSaveReq editReq) {
        Article oldArticle = articleRepository.getById(editReq.getId());
        if (oldArticle == null) {
            throw new FcBusinessException(FcErrorCodeEnum.BAD_REQUEST, "文章不存在");
        }
        // TODO : by mfc on 2024/8/25 暂时不做用户限制
        Article article = ArticleConverter.INSTANCE.toArticle(editReq);
        articleRepository.updateById(article);
        if (editReq.getEditContent()) {
            articleDetailRepository.updateById(ArticleConverter.INSTANCE.toArticleDetail(article, editReq));
        }
        return article.getId();
    }

    @Override
    public ArticleDetailRes getDetail(Integer id, Boolean needTemplate) {
        Article article = articleRepository.getById(id);
        ArticleDetail articleDetail = articleDetailRepository.getById(id);
        if (needTemplate && articleDetail != null && articleDetail.getTemplateId() != null) {
            ArticleTemplate template = teamRepository.getById(articleDetail.getTemplateId());
            if (template != null) {
                articleDetail.setContentMd(template.getHeaderContentMd()
                        + articleDetail.getContentMd()
                        + template.getFooterContentMd());
            }
        }
        return ArticleConverter.INSTANCE.toArticleDetailRes(article, articleDetail);
    }

    @Override
    public Boolean incrReadCt(Integer id) {
        return articleRepository.lambdaUpdate()
                .setSql("read_ct = read_ct + 1")
                .eq(Article::getId, id)
                .update();
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
    public Boolean uptArticleStatus(Integer id, Boolean status) {
        return articleRepository.lambdaUpdate()
                .eq(Article::getId, id)
                .set(Article::getStatus, status)
                .update();
    }

    @Override
    public Boolean initOrderNum() {
        List<Article> articleList = articleRepository.lambdaQuery()
                .select(Article::getId)
                .orderByAsc(Article::getOrderNum)
                .orderByDesc(BaseEntity::getCreateTime, Article::getId)
                .list();
        for (int i = 0; i < articleList.size(); i++) {
            articleList.get(i).setOrderNum(i * 10 + 9);
        }
        return articleRepository.updateBatchById(articleList);
    }


    @Override
    public List<ArticleRes> listByIds(List<Integer> articleIdList) {
        if (CollUtil.isEmpty(articleIdList)) {
            return new ArrayList<>();
        }
        List<Article> articleList = articleRepository.listByIds(articleIdList);
        return ArticleConverter.INSTANCE.articleListToResList(articleList);
    }


    @Override
    public FcPageRes<ArticleTemplateRes> pageArticleTemplate(ArticleTemplatePageReq pageReq) {
        Page<ArticleTemplate> page = teamRepository.lambdaQuery()
                .like(StrUtil.isNotBlank(pageReq.getTitle()), ArticleTemplate::getTitle, pageReq.getTitle())
                .orderByDesc(BaseEntity::getCreateTime, ArticleTemplate::getId)
                .page(new Page<>(pageReq.getPage(), pageReq.getPageSize()));
        return new FcPageRes<ArticleTemplateRes>(pageReq)
                .total(page.getTotal())
                .records(ArticleConverter.INSTANCE.templateListToResList(page.getRecords()));
    }


    @Component
    static class ArticleTemplateRepository extends ServiceImpl<ArticleTemplateMapper, ArticleTemplate> {

    }


    @Component
    static class ArticleRepository extends ServiceImpl<ArticleMapper, Article> {

    }


    @Component
    static class ArticleDetailRepository extends ServiceImpl<ArticleDetailMapper, ArticleDetail> {

    }
}
