package cn.fangcai.blog.core.service.impl;

import cn.fangcai.blog.consts.ArticleOrderByFiledEnum;
import cn.fangcai.blog.core.mapper.ArticleDetailMapper;
import cn.fangcai.blog.core.mapper.ArticleMapper;
import cn.fangcai.blog.core.mapper.ArticleTemplateMapper;
import cn.fangcai.blog.core.model.entity.Article;
import cn.fangcai.blog.core.model.entity.ArticleDetail;
import cn.fangcai.blog.core.model.entity.ArticleTemplate;
import cn.fangcai.blog.core.model.entity.base.BaseEntityWithDel;
import cn.fangcai.blog.core.model.req.ArticlePageReq;
import cn.fangcai.blog.core.model.req.ArticleSaveReq;
import cn.fangcai.blog.core.model.req.ArticleTemplatePageReq;
import cn.fangcai.blog.core.model.res.ArticleDetailRes;
import cn.fangcai.blog.core.model.res.ArticleRes;
import cn.fangcai.blog.core.model.res.ArticleTemplateRes;
import cn.fangcai.blog.core.service.IArticleService;
import cn.fangcai.blog.core.service.ICourseService;
import cn.fangcai.blog.mapstruct.ArticleConverter;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.enums.FcErrorCodeEnum;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Autowired
    @Lazy
    private ICourseService courseService;

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
        if (article == null) {
            throw new FcBusinessException(FcErrorCodeEnum.BAD_REQUEST, "文章不存在");
        }
        ArticleDetail articleDetail = articleDetailRepository.getById(id);
        if (needTemplate && articleDetail != null && article.getTemplateId() != null) {
            ArticleTemplate template = teamRepository.getById(article.getTemplateId());
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
        Integer excludeCourseId = pageReq.getExcludeCourseId();
        List<Integer> excludeArticleIdList = new ArrayList<>();
        if (excludeCourseId != null) {
            excludeArticleIdList = courseService.listArticleIdById(excludeCourseId);
        }
        Page<Article> page = articleRepository.lambdaQuery()
                .like(StrUtil.isNotBlank(pageReq.getTitle()), Article::getTitle, pageReq.getTitle())
                .eq(!Objects.isNull(pageReq.getStatus()), Article::getStatus, pageReq.getStatus())
                .eq(!Objects.isNull(pageReq.getLimitType()), Article::getLimitType, pageReq.getLimitType())
                .notIn(CollUtil.isNotEmpty(excludeArticleIdList), Article::getId, excludeArticleIdList)
                .orderBy(true, pageReq.getIsAsc(), ArticleOrderByFiledEnum.getOrderByFiled(pageReq.getOrderField()))
                .orderByDesc(Article::getId)
                .page(new Page<>(pageReq.getPage(), pageReq.getPageSize()));
        List<ArticleRes> articleResList = ArticleConverter.INSTANCE.articleListToResList(page.getRecords());
        if (CollUtil.isNotEmpty(articleResList)) {
            List<Integer> articleIdList = articleResList.stream().map(ArticleRes::getId).toList();
            Map<Integer, Integer> articleIdAndCourseIdMap = courseService.getMapByArticleIds(articleIdList);
            articleResList.forEach(article -> article.setCourseId(articleIdAndCourseIdMap.get(article.getId())));
        }
        return new FcPageRes<ArticleRes>(pageReq)
                .total(page.getTotal())
                .records(articleResList);
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
                .orderByDesc(Article::getId)
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
                .orderByDesc(BaseEntityWithDel::getCreateTime, ArticleTemplate::getId)
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
