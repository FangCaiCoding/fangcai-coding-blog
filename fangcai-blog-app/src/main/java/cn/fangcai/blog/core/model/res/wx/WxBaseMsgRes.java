package cn.fangcai.blog.core.model.res.wx;

import cn.fangcai.blog.core.model.req.wx.WxTxtMsgReq;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author MouFangCai
 * @date 2024/10/26 18:40
 * @description
 */
@Schema(description = "微信消息响应基础对象")
@Data
@JacksonXmlRootElement(localName = "xml")
public class WxBaseMsgRes {

    @JacksonXmlProperty(localName = "ToUserName")
    @JacksonXmlCData
    private String toUserName;

    @JacksonXmlProperty(localName = "FromUserName")
    @JacksonXmlCData
    private String fromUserName;

    @JacksonXmlProperty(localName = "CreateTime")
    @JacksonXmlCData
    private Long createTime;

    @JacksonXmlProperty(localName = "MsgType")
    @JacksonXmlCData
    private String msgType;


    public void fillBaseInfo(WxTxtMsgReq msgReq) {
        this.setFromUserName(msgReq.getToUserName());
        this.setToUserName(msgReq.getFromUserName());
        this.setCreateTime(System.currentTimeMillis() / 1000);
    }
}