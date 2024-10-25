package cn.fangcai.blog.controller;

import cn.fangcai.blog.model.req.website.WebSitePageReq;
import cn.fangcai.blog.model.req.website.WebsiteSaveReq;
import cn.fangcai.blog.model.res.website.WebsiteCateRes;
import cn.fangcai.blog.model.res.website.WebsiteListRes;
import cn.fangcai.blog.model.res.website.WebsiteRes;
import cn.fangcai.blog.service.IWebsiteService;
import cn.fangcai.common.auth.FcAuthContext;
import cn.fangcai.common.auth.ano.FcNotCheckLogin;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源站点信息 前端控制器
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-18
 */
@RestController
@RequestMapping("/website")
@Tag(name = "网站管理")
public class WebsiteController {

    @Autowired
    private IWebsiteService websiteService;

    @Operation(summary = "新增网站信息")
    @PostMapping
    public FcResult<Integer> save(@RequestBody @Validated WebsiteSaveReq saveReq) {
        saveReq.setUserId(FcAuthContext.getUserIdAsInt());
        return FcResult.SUCCESS(websiteService.save(saveReq));
    }

    @Operation(summary = "更新网站状态")
    @PutMapping("/uptStatus/{id}/{status}")
    public FcResult<Boolean> uptStatus(@PathVariable("id") Integer id,
                                       @PathVariable("status") Byte status) {
        return FcResult.SUCCESS(websiteService.uptStatus(id, status));
    }


    @Operation(summary = "删除网站信息")
    @DeleteMapping("/{id}")
    public FcResult<Boolean> delete(@PathVariable("id") Integer id) {
        return FcResult.SUCCESS(websiteService.delete(id));
    }

    @Operation(summary = "分页查询网站信息")
    @PostMapping("/page")
    public FcResult<FcPageRes<WebsiteRes>> pageByReq(@RequestBody @Validated WebSitePageReq pageReq) {
        return FcResult.SUCCESS(websiteService.pageByReq(pageReq));
    }


    /**
     * 公开类接口
     */
    @Operation(summary = "获取所有公开的站点")
    @GetMapping("/public/list/all")
    @FcNotCheckLogin
    public FcResult<List<WebsiteListRes>> listPublicAll() {
        return FcResult.SUCCESS(websiteService.listPublicAll());
    }

    @Operation(summary = "获取所有站点分类")
    @GetMapping("/public/cate/all")
    @FcNotCheckLogin
    public FcResult<List<WebsiteCateRes>> listAllCate() {
        return FcResult.SUCCESS(websiteService.listAllCate());
    }

    @Operation(summary = "点击网站")
    @PutMapping("/click/{id}")
    @FcNotCheckLogin
    public FcResult<Boolean> clickWebsite(@PathVariable Integer id) {
        return FcResult.SUCCESS(websiteService.clickWebsite(id));
    }

}
