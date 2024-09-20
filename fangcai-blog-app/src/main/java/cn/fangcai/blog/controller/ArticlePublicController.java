package cn.fangcai.blog.controller;

import cn.fangcai.blog.consts.ArticleStatusEnum;
import cn.fangcai.blog.consts.BlogErrorCodeEnum;
import cn.fangcai.blog.model.req.ArticlePageReq;
import cn.fangcai.blog.model.req.CoursePageReq;
import cn.fangcai.blog.model.res.ArticleDetailRes;
import cn.fangcai.blog.model.res.ArticleRes;
import cn.fangcai.blog.model.res.CourseRes;
import cn.fangcai.blog.service.IArticleService;
import cn.fangcai.blog.service.ICourseService;
import cn.fangcai.common.auth.ano.FcNotCheckLogin;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.common.model.exception.FcBusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author MouFangCai
 * @since 2024-08-25
 */
@RestController
@RequestMapping("/article/public")
@Tag(name = "文章 公开的api")
@FcNotCheckLogin
public class ArticlePublicController {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private ICourseService courseService;

    @Operation(summary = "获取文章详情")
    @GetMapping("/{id}")
    public FcResult<ArticleDetailRes> getDetail(@PathVariable Integer id) {
        ArticleDetailRes detail = articleService.getDetail(id);
        if (detail != null && !ArticleStatusEnum.PUBLISHED.getCode().equals(detail.getStatus())) {
            throw new FcBusinessException(BlogErrorCodeEnum.ARTICLE_UN_PUBLISHED);
        }
        articleService.incrReadCt(id);
        return FcResult.SUCCESS(detail);
    }

    @Operation(summary = "分页查询文章")
    @PostMapping("/page")
    public FcResult<FcPageRes<ArticleRes>> pageArticle(@RequestBody @Validated ArticlePageReq pageReq) {
        pageReq.setStatus(ArticleStatusEnum.PUBLISHED.getCode());
        return FcResult.SUCCESS(articleService.pageArticle(pageReq));
    }


    @Operation(summary = "获取文章教程")
    @GetMapping("/course/{id}")
    public FcResult<CourseRes> getById(@PathVariable Integer id) {
        courseService.incrReadCt(id);
        return FcResult.SUCCESS(courseService.getById(id));
    }

    @Operation(summary = "分页查询教程")
    @PostMapping("/course/page")
    public FcResult<FcPageRes<CourseRes>> pageCourse(@RequestBody @Validated CoursePageReq pageReq) {
        pageReq.setStatus(ArticleStatusEnum.PUBLISHED.getCode());
        return FcResult.SUCCESS(courseService.pageCourse(pageReq));
    }

}
