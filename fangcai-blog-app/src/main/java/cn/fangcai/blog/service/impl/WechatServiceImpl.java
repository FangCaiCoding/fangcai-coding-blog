package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.controller.ICacheService;
import cn.fangcai.blog.model.req.wx.WxTxtMsgReq;
import cn.fangcai.blog.model.res.wx.WxTxtMsgRes;
import cn.fangcai.blog.service.IWechatService;
import cn.fangcai.blog.uitls.CacheKeyFactory;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MouFangCai
 * @date 2024/10/26 18:48
 * @description
 */
@Service
@Slf4j
public class WechatServiceImpl implements IWechatService {
    @Autowired
    private ICacheService cacheService;

    private final static Map<String, String> WX_MSG_MAP;


    static {
        WX_MSG_MAP = new HashMap<>();

        WX_MSG_MAP.put("default", "\n遇见即是缘分，方才兄希望与你一起 From Zero To Hero！\n" +
                "<a href=\"http://mp.weixin.qq.com/mp/homepage?__biz=MzIxMjE3NjYwOQ==&hid=5&sn=b9c85139ca524fc7823379f4a2da99ba&scene=18#wechat_redirect\">快速入口-ElasticSearch系列</a>\n" +
                "<a  href=\"https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzIxMjE3NjYwOQ==&action=getalbum&album_id=3643516599190781953#wechat_redirect\">快速入口-前端入门系列</a>\n" +
                "<a href=\"https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIxMjE3NjYwOQ==&scene=123#wechat_redirect\">快速入口-往期精彩</a>\n" +
                "回复【es】，获取ElasticSearch系列知识图谱\n" +
                "回复【软考】，获取软考-系统架构师备考资料\n" +
                "如果还有其他问题，那就【<a href=\"https://mp.weixin.qq.com/s/vxNNjyvwqlLwlADipG0lyg\">加入社群</a>】");

        WX_MSG_MAP.put("subscribe", "Hello！^~^欢迎来到【方才编程】\n" +
                "\n" +
                "只有系统学习，才能灵活运用！\n" +
                "\n" +
                "“学编程，一定要系统化”是方才兄一直坚持的学习之道！\n");

        WX_MSG_MAP.put("es", "《ElasticSearch系列知识图谱》\n" +
                "链接：https://pan.baidu.com/s/1uVFNTXmHLORmz4dKOcKqIQ \n" +
                "提取码：hero\n");

        WX_MSG_MAP.put("es实战", "gitee地址：https://gitee.com/moufangcai/learningEs\n");

        WX_MSG_MAP.put("软考", "gitee地址：https://gitee.com/moufangcai/learningEs\n");
    }


    /**
     * 处理微信的消息-用于自动回复和登录
     *
     * @return WxMsgRes
     */
    @Override
    public WxTxtMsgRes handleWxMsg(WxTxtMsgReq wxMsgReq) {
        String event = wxMsgReq.getEvent();
        String content = wxMsgReq.getContent();
        String textRes = "";
        String newSubscribe = "subscribe";
        String loginCode = "666";
        if (loginCode.equals(content)) {
            String captcha = RandomUtil.randomNumbers(6);
            cacheService.put(CacheKeyFactory.getWxLoginKey(captcha), wxMsgReq.getFromUserName(), 30);
            textRes = "【方才coding】登录验证码：" + captcha + " ，请在30秒内输入。";
        } else if (newSubscribe.equalsIgnoreCase(event)) {
            textRes = WX_MSG_MAP.get(newSubscribe.toLowerCase())+ WX_MSG_MAP.get("default");
        } else {
            textRes = WX_MSG_MAP.getOrDefault(content, "/:? 还在找其它资料么？嘿嘿，后面会越来越多的。\n");
        }
        log.debug("handleWxMsg-textRes:[{}]", textRes);
        WxTxtMsgRes msgRes = new WxTxtMsgRes();
        msgRes.setContent(textRes);
        msgRes.setMsgType("text");
        msgRes.fillUser(wxMsgReq);
        return msgRes;
    }

}
