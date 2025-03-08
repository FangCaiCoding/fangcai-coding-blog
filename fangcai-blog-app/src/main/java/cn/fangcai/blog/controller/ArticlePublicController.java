package cn.fangcai.blog.controller;

import cn.fangcai.blog.consts.BlogErrorCodeEnum;
import cn.fangcai.blog.consts.StatusEnum;
import cn.fangcai.blog.core.model.req.ArticlePageReq;
import cn.fangcai.blog.core.model.req.CoursePageReq;
import cn.fangcai.blog.core.model.res.ArticleDetailRes;
import cn.fangcai.blog.core.model.res.ArticleRes;
import cn.fangcai.blog.core.model.res.CourseRes;
import cn.fangcai.blog.core.service.IArticleService;
import cn.fangcai.blog.core.service.ICourseService;
import cn.fangcai.blog.uitls.FcStrUtil;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.fangcai.starter.auth.FcAuthUtil;
import cn.fangcai.starter.auth.ano.FcNotCheckLogin;
import cn.fangcai.starter.log.ano.FcLog;
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
    @FcLog(desc = "阅读文章: %s", respEl = "data.title", actionType = FcLog.ActionType.SELECT)
    public FcResult<ArticleDetailRes> getDetail(@PathVariable Integer id) {
        ArticleDetailRes detail = articleService.getDetail(id, true);
        if (detail == null || !StatusEnum.PUBLISHED.getCode().equals(detail.getStatus())) {
            throw new FcBusinessException(BlogErrorCodeEnum.ARTICLE_UN_PUBLISHED);
        }
        if (!FcAuthUtil.isLogin() && detail.getReadLimitRatio() != null && detail.getReadLimitRatio() < 100) {
            detail.setContentMd(FcStrUtil.subStrByLinesLimit(detail.getContentMd(), detail.getReadLimitRatio()));
            detail.setContendIsEnd(false);
        }
        articleService.incrReadCt(id);
        return FcResult.SUCCESS(detail);
    }

    @Operation(summary = "分页查询文章")
    @PostMapping("/page")
    @FcLog(desc = "分页查询文章", actionType = FcLog.ActionType.SELECT)
    public FcResult<FcPageRes<ArticleRes>> pageArticle(@RequestBody @Validated ArticlePageReq pageReq) {
        pageReq.setStatus(StatusEnum.PUBLISHED.getCode());
        return FcResult.SUCCESS(articleService.pageArticle(pageReq));
    }


    @Operation(summary = "获取文章教程")
    @GetMapping("/course/{id}")
    @FcLog(desc = "阅读教程：%s", respEl = "data.title", actionType = FcLog.ActionType.SELECT)
    public FcResult<CourseRes> getById(@PathVariable Integer id) {
        CourseRes courseRes = courseService.getById(id);
        if (courseRes == null || !StatusEnum.PUBLISHED.getCode().equals(courseRes.getStatus())) {
            throw new FcBusinessException(BlogErrorCodeEnum.COURSE_UN_PUBLISHED);
        }
        courseService.incrReadCt(id);
        return FcResult.SUCCESS(courseRes);
    }

    @Operation(summary = "分页查询教程")
    @PostMapping("/course/page")
    @FcLog(desc = "分页查询教程", actionType = FcLog.ActionType.SELECT)
    public FcResult<FcPageRes<CourseRes>> pageCourse(@RequestBody @Validated CoursePageReq pageReq) {
        pageReq.setStatus(StatusEnum.PUBLISHED.getCode());
        return FcResult.SUCCESS(courseService.pageCourse(pageReq));
    }

}
