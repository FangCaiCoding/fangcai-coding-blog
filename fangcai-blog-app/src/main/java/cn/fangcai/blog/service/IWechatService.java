package cn.fangcai.blog.service;

import cn.fangcai.blog.model.req.wx.WxTxtMsgReq;
import cn.fangcai.blog.model.res.wx.WxBaseMsgRes;
import cn.fangcai.blog.model.res.wx.WxTxtMsgRes;

/**
 * @author MouFangCai
 * @date 2024/10/26 18:48
 * @description
 */
public interface IWechatService {
    WxBaseMsgRes handleWxMsg(WxTxtMsgReq wxMsgReq);
}
