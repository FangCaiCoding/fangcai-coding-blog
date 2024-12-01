package cn.fangcai.blog.controller.admin;

import cn.fangcai.blog.core.model.req.ArticlePageReq;
import cn.fangcai.blog.core.model.req.ArticleSaveReq;
import cn.fangcai.blog.core.model.req.ArticleTemplatePageReq;
import cn.fangcai.blog.core.model.res.ArticleDetailRes;
import cn.fangcai.blog.core.model.res.ArticleRes;
import cn.fangcai.blog.core.model.res.ArticleTemplateRes;
import cn.fangcai.blog.core.service.IArticleService;
import cn.fangcai.starter.auth.FcAuthContext;
import cn.fangcai.starter.auth.ano.FcCheckAuth;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;
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
@RequestMapping("/article")
@Tag(name = "文章管理")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Operation(summary = "新增文章")
    @PostMapping
    @FcCheckAuth(values = {"article:add"})
    public FcResult<Integer> addArticle(@RequestBody @Validated ArticleSaveReq saveReq) {
        saveReq.setUserId(FcAuthContext.getUserIdAsInt());
        return FcResult.SUCCESS(articleService.addArticle(saveReq));
    }

    @Operation(summary = "编辑文章")
    @PutMapping
    @FcCheckAuth(values = {"article:edit"})
    public FcResult<Integer> editArticle(@RequestBody @Validated ArticleSaveReq editReq) {
        return FcResult.SUCCESS(articleService.editArticle(editReq));
    }

    @Operation(summary = "获取文章详情")
    @GetMapping("/{id}")
    @FcCheckAuth(values = {"article:detail"})
    public FcResult<ArticleDetailRes> getDetail(@PathVariable Integer id) {
        return FcResult.SUCCESS(articleService.getDetail(id, false));
    }


    @Operation(summary = "分页查询文章")
    @PostMapping("/page")
    @FcCheckAuth(values = {"article:page"})
    public FcResult<FcPageRes<ArticleRes>> pageArticle(@RequestBody @Validated ArticlePageReq pageReq) {
        return FcResult.SUCCESS(articleService.pageArticle(pageReq));
    }

    @Operation(summary = "删除文章")
    @DeleteMapping("/{id}")
    @FcCheckAuth(values = {"article:del"})
    public FcResult<Boolean> delArticle(@PathVariable Integer id) {
        return FcResult.SUCCESS(articleService.delArticle(id));
    }

    @Operation(summary = "修改文章状态")
    @PutMapping("/status/{id}/{status}")
    @FcCheckAuth(values = {"article:status"})
    public FcResult<Boolean> uptArticleStatus(@PathVariable Integer id, @PathVariable Boolean status) {
        return FcResult.SUCCESS(articleService.uptArticleStatus(id, status));
    }

    @Operation(summary = "重置顺序号")
    @PutMapping("/initOrderNum")
    @FcCheckAuth(values = {"article:initOrderNum"})
    public FcResult<Boolean> initOrderNum() {
        return FcResult.SUCCESS(articleService.initOrderNum());
    }


    /**
     * 文章模板相关
     */

    @Operation(summary = "分页查询文章")
    @PostMapping("/template/page")
    @FcCheckAuth(values = {"article:template:page"})
    public FcResult<FcPageRes<ArticleTemplateRes>> pageArticleTemplate(@RequestBody @Validated ArticleTemplatePageReq pageReq) {
        return FcResult.SUCCESS(articleService.pageArticleTemplate(pageReq));
    }

}
