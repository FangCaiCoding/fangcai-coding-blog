package cn.fangcai.blog.core.service;

import cn.fangcai.blog.core.model.res.wx.WxBaseMsgRes;
import cn.fangcai.blog.core.model.req.wx.WxTxtMsgReq;

/**
 * @author MouFangCai
 * @date 2024/10/26 18:48
 * @description
 */
public interface IWechatService {
    WxBaseMsgRes handleWxMsg(WxTxtMsgReq wxMsgReq);
}
