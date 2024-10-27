package cn.fangcai.blog.controller;

import cn.fangcai.blog.config.BlogAppProperties;
import cn.fangcai.blog.consts.BlogErrorCodeEnum;
import cn.fangcai.blog.model.req.wx.WxTxtMsgReq;
import cn.fangcai.blog.model.res.wx.WxTxtMsgRes;
import cn.fangcai.blog.service.IWechatService;
import cn.fangcai.common.auth.ano.FcNotCheckLogin;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author MouFangCai
 * @date 2024/10/26 18:25
 * @description
 */
@RestController
@RequestMapping("/wechat")
@FcNotCheckLogin
@Slf4j
@Tag(name = "微信公众号接入")
public class WechatController {


    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private BlogAppProperties blogAppProperties;
    @Autowired
    private IWechatService wechatService;

    /**
     * 微信的公众号接入 token 验证，即返回echostr的参数值
     *
     * @param request
     *
     * @return
     */
    @GetMapping("handleWxMsg")
    public String handleWxMsg(HttpServletRequest request) {
        /**
         * {"signature":["55671363b339d3431f3eed196da2cd9e71198934"],
         * "echostr":["1665396725302394511"],
         * "timestamp":["1687575055"],
         * "nonce":["1688964723"]}
         */
        log.debug("handleWxMsg:【{}】", JSONUtil.toJsonStr(request.getParameterMap()));
        String echoStr = request.getParameter("echostr");
        Boolean success = this.checkWxSignature(request);
        if (success && StrUtil.isNotBlank(echoStr)) {
            return echoStr;
        }
        return "";
    }


    /**
     * 微信的响应返回
     *
     * @param msg
     *
     * @return
     */
    @PostMapping(path = "handleWxMsg",
            consumes = {"application/xml", "text/xml"},
            produces = "application/xml;charset=utf-8")
    public WxTxtMsgRes handleWxMsg(@RequestBody WxTxtMsgReq msg,
                                   HttpServletRequest request) {
        log.debug("handleWxMsg-WxTxtMsgReq:[{}]", JSONUtil.toJsonStr(msg));
        Boolean success = this.checkWxSignature(request);
        if (!success) {
            throw new FcBusinessException(BlogErrorCodeEnum.WX_SIGNATURE_ERROR);
        }
        return wechatService.handleWxMsg(msg);
    }


    /**
     * 校验是否 来自微信服务器
     *
     * @param request
     *
     * @return
     */
    private Boolean checkWxSignature(HttpServletRequest request) {
        if ("dev".equals(env)) {
            return true;
        }
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String wxToken = blogAppProperties.getWxToken();
        String[] arr = new String[]{wxToken, timestamp, nonce};
        Arrays.sort(arr);
        String tmpSignature = DigestUtil.sha1Hex(ArrayUtil.join(arr, ""));
        return tmpSignature != null && tmpSignature.equalsIgnoreCase(signature);
    }
}
