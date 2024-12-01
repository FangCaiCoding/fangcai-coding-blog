package cn.fangcai.blog.core.model.res.wx;

import cn.fangcai.blog.consts.WechatMsgTypeEnum;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MouFangCai
 * @date 2024/10/26 18:40
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class WxTxtMsgRes extends WxBaseMsgRes {


    @JacksonXmlProperty(localName = "Content")
    @JacksonXmlCData
    private String content;

    public WxTxtMsgRes(String content) {
        this.content = content;
        this.setMsgType(WechatMsgTypeEnum.TEXT.getWxFlag());
    }
}