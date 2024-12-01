package cn.fangcai.blog.controller.admin;

import cn.fangcai.blog.core.model.req.website.WebSitePageReq;
import cn.fangcai.blog.core.model.req.website.WebsiteSaveReq;
import cn.fangcai.blog.core.model.res.website.WebsiteRes;
import cn.fangcai.blog.core.service.IWebsiteService;
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
    @FcCheckAuth(values = {"website:save"})
    public FcResult<Integer> save(@RequestBody @Validated WebsiteSaveReq saveReq) {
        saveReq.setUserId(FcAuthContext.getUserIdAsInt());
        return FcResult.SUCCESS(websiteService.save(saveReq));
    }

    @Operation(summary = "更新网站状态")
    @PutMapping("/uptStatus/{id}/{status}")
    @FcCheckAuth(values = {"website:uptStatus"})
    public FcResult<Boolean> uptStatus(@PathVariable("id") Integer id,
                                       @PathVariable("status") Byte status) {
        return FcResult.SUCCESS(websiteService.uptStatus(id, status));
    }


    @Operation(summary = "删除网站信息")
    @DeleteMapping("/{id}")
    @FcCheckAuth(values = {"website:delete"})
    public FcResult<Boolean> delete(@PathVariable("id") Integer id) {
        return FcResult.SUCCESS(websiteService.delete(id));
    }

    @Operation(summary = "分页查询网站信息")
    @PostMapping("/page")
    @FcCheckAuth(values = {"website:page"})
    public FcResult<FcPageRes<WebsiteRes>> pageByReq(@RequestBody @Validated WebSitePageReq pageReq) {
        return FcResult.SUCCESS(websiteService.pageByReq(pageReq));
    }

}
