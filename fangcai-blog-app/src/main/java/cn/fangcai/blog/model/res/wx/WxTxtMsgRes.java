package cn.fangcai.blog.model.res.wx;

import cn.fangcai.blog.model.req.wx.WxTxtMsgReq;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * @author MouFangCai
 * @date 2024/10/26 18:40
 * @description
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class WxTxtMsgRes {

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

    @JacksonXmlProperty(localName = "Content")
    @JacksonXmlCData
    private String content;

    public void fillUser(WxTxtMsgReq msgReq) {
        this.setFromUserName(msgReq.getToUserName());
        this.setToUserName(msgReq.getFromUserName());
        this.setCreateTime(System.currentTimeMillis() / 1000);
    }
}