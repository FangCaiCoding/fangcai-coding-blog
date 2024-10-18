package cn.fangcai.blog.controller;

import cn.fangcai.blog.model.req.website.WebsiteSaveReq;
import cn.fangcai.blog.model.res.website.WebsiteListRes;
import cn.fangcai.blog.service.IWebsiteService;
import cn.fangcai.common.auth.FcAuthContext;
import cn.fangcai.common.auth.ano.FcNotCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
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
public class WebsiteController {

    @Autowired
    private IWebsiteService websiteService;

    @Operation(summary = "新增网站信息")
    @PostMapping
    public Integer save(@RequestBody @Validated WebsiteSaveReq saveReq) {
        saveReq.setUserId(FcAuthContext.getUserIdAsInt());
        return websiteService.save(saveReq);
    }

    @Operation(summary = "更新网站状态")
    @PutMapping("uptStatus")
    public Boolean uptStatus(@RequestParam("id") Integer id,
                             @RequestParam("status") Byte status) {
        return websiteService.uptStatus(id, status);
    }


    /**
     * 公开类接口
     */
    @Operation(summary = "获取所有公开的站点")
    @GetMapping("/public/list/all")
    @FcNotCheckLogin
    public List<WebsiteListRes> listPublicAll() {
        return websiteService.listPublicAll();
    }


}
