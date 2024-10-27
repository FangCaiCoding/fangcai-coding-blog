package cn.fangcai.blog.controller;

import cn.fangcai.blog.model.res.website.WebsiteCateRes;
import cn.fangcai.blog.model.res.website.WebsiteListRes;
import cn.fangcai.blog.service.IWebsiteService;
import cn.fangcai.common.auth.ano.FcNotCheckLogin;
import cn.fangcai.common.model.dto.FcResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/website/public/")
@Tag(name = "网站管理-公开部分")
@FcNotCheckLogin
public class WebsitePublicController {

    @Autowired
    private IWebsiteService websiteService;

    /**
     * 公开类接口
     */
    @Operation(summary = "获取所有公开的站点")
    @GetMapping("/list/all")
    public FcResult<List<WebsiteListRes>> listPublicAll() {
        return FcResult.SUCCESS(websiteService.listPublicAll());
    }

    @Operation(summary = "获取所有站点分类")
    @GetMapping("/cate/all")
    public FcResult<List<WebsiteCateRes>> listAllCate() {
        return FcResult.SUCCESS(websiteService.listAllCate());
    }

    @Operation(summary = "点击网站")
    @PutMapping("/click/{id}")
    public FcResult<Boolean> clickWebsite(@PathVariable Integer id) {
        return FcResult.SUCCESS(websiteService.clickWebsite(id));
    }

}
