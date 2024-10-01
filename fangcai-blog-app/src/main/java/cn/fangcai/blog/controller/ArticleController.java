package cn.fangcai.blog.controller;

import cn.fangcai.blog.model.req.ArticlePageReq;
import cn.fangcai.blog.model.req.ArticleSaveReq;
import cn.fangcai.blog.model.res.ArticleDetailRes;
import cn.fangcai.blog.model.res.ArticleRes;
import cn.fangcai.blog.service.IArticleService;
import cn.fangcai.common.auth.FcAuthContext;
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
    public FcResult<Integer> addArticle(@RequestBody @Validated ArticleSaveReq saveReq) {
        saveReq.setUserId(FcAuthContext.getUserIdAsInt());
        return FcResult.SUCCESS(articleService.addArticle(saveReq));
    }

    @Operation(summary = "编辑文章")
    @PutMapping
    public FcResult<Integer> editArticle(@RequestBody @Validated ArticleSaveReq editReq) {
        return FcResult.SUCCESS(articleService.editArticle(editReq));
    }

    @Operation(summary = "获取文章详情")
    @GetMapping("/{id}")
    public FcResult<ArticleDetailRes> getDetail(@PathVariable Integer id) {
        return FcResult.SUCCESS(articleService.getDetail(id,false));
    }


    @Operation(summary = "分页查询文章")
    @PostMapping("/page")
    public FcResult<FcPageRes<ArticleRes>> pageArticle(@RequestBody @Validated ArticlePageReq pageReq) {
        return FcResult.SUCCESS(articleService.pageArticle(pageReq));
    }

    @Operation(summary = "删除文章")
    @DeleteMapping("/{id}")
    public FcResult<Boolean> delArticle(@PathVariable Integer id) {
        return FcResult.SUCCESS(articleService.delArticle(id));
    }

    @Operation(summary = "修改文章状态")
    @PutMapping("/status/{id}/{status}")
    public FcResult<Boolean> uptArticleStatus(@PathVariable Integer id, @PathVariable Boolean status) {
        return FcResult.SUCCESS(articleService.uptArticleStatus(id, status));
    }

    @Operation(summary = "置顶文章")
    @PutMapping("/pinTop/{id}")
    public FcResult<Boolean> pinTopArticle(@PathVariable Integer id) {
        return FcResult.SUCCESS(articleService.pinTopArticle(id));
    }

    @Operation(summary = "修改文章顺序")
    @PutMapping("/orderNum/{id}")
    public FcResult<Boolean> uptOrderNum(@PathVariable Integer id, @RequestParam Integer targetId) {
        return FcResult.SUCCESS(articleService.uptOrderNum(id, targetId));
    }
}
