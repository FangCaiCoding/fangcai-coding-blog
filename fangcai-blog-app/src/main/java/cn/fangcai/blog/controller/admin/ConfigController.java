package cn.fangcai.blog.controller.admin;

import cn.fangcai.blog.model.entity.ConfigWechat;
import cn.fangcai.blog.model.req.wx.ConfigWechatSaveReq;
import cn.fangcai.blog.service.IConfigService;
import cn.fangcai.starter.auth.ano.FcCheckAuth;
import cn.fangcai.common.model.dto.FcResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 网站配置相关 前端控制器
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-18
 */
@RestController
@RequestMapping("/config")
@Tag(name = "网站配置相关")
public class ConfigController {

    @Autowired
    private IConfigService configService;

    @Operation(summary = "保存微信相关的配置")
    @PostMapping("wechat")
    @FcCheckAuth(values = {"config:wechat:save"})
    public FcResult<Integer> saveWechat(@RequestBody @Validated ConfigWechatSaveReq wechatSaveReq) {
        return FcResult.SUCCESS(configService.saveWechat(wechatSaveReq));
    }

    @Operation(summary = "分页查询微信相关的配置")
    @GetMapping("wechat/list")
    @FcCheckAuth(values = {"config:wechat:list"})
    public FcResult<List<ConfigWechat>> listWechat() {
        return FcResult.SUCCESS(configService.listWechat(null));
    }

}
