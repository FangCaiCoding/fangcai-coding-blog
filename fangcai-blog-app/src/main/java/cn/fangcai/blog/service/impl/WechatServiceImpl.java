package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.consts.WechatMsgTypeEnum;
import cn.fangcai.blog.model.entity.ConfigWechat;
import cn.fangcai.blog.model.req.wx.WxTxtMsgReq;
import cn.fangcai.blog.model.res.wx.WxBaseMsgRes;
import cn.fangcai.blog.model.res.wx.WxImageMsgRes;
import cn.fangcai.blog.model.res.wx.WxTxtMsgRes;
import cn.fangcai.blog.service.ICacheService;
import cn.fangcai.blog.service.IConfigService;
import cn.fangcai.blog.service.IWechatService;
import cn.fangcai.blog.uitls.CacheKeyFactory;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    private IConfigService configService;


    /**
     * 处理微信的消息-用于自动回复和登录
     *
     * @return WxMsgRes
     */
    @Override
    public WxBaseMsgRes handleWxMsg(WxTxtMsgReq wxMsgReq) {
        String event = wxMsgReq.getEvent();
        String content = wxMsgReq.getContent();
        String newSubscribeKey = "subscribe";
        String loginCodeKey = "666";
        String defaultKey = "default";
        Map<String, ConfigWechat> wechatConfigMap = configService.listWechat(true)
                .stream()
                .collect(Collectors.toMap(ConfigWechat::getKeyStr, Function.identity(), (k1, k2) -> k2));
        ConfigWechat matchedConfig = null;
        if (loginCodeKey.equals(content)) {
            String captcha = RandomUtil.randomNumbers(6);
            cacheService.put(CacheKeyFactory.getWxLoginKey(captcha), wxMsgReq.getFromUserName(), 30);
            String msg = "【方才coding】登录验证码：" + captcha + " ，请在30秒内输入。";
            matchedConfig = new ConfigWechat();
            matchedConfig.setMsgValue(msg);
            matchedConfig.setMsgType(WechatMsgTypeEnum.TEXT.getCode());
        } else if (newSubscribeKey.equalsIgnoreCase(event)) {
            matchedConfig = wechatConfigMap.getOrDefault(newSubscribeKey, new ConfigWechat());
        } else {
            matchedConfig = wechatConfigMap.get(content);
        }
        if (matchedConfig == null) {
            matchedConfig = wechatConfigMap.get(defaultKey);
        }
        log.debug("handleWxMsg-textRes:[{}]", JSONUtil.toJsonStr(matchedConfig));
        WxBaseMsgRes msgRes;
        if (WechatMsgTypeEnum.IMAGE.getCode().equals(matchedConfig.getMsgType())) {
            msgRes = new WxImageMsgRes(matchedConfig.getMsgValue());
        } else {
            msgRes = new WxTxtMsgRes(matchedConfig.getMsgValue());
        }
        msgRes.fillBaseInfo(wxMsgReq);
        return msgRes;
    }


}
