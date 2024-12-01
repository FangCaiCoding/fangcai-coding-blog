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
public class WxImageMsgRes extends WxBaseMsgRes {


    @JacksonXmlProperty(localName = "Image")
    private WxImageInfo image;

    @Data
    static class WxImageInfo {

        @JacksonXmlProperty(localName = "MediaId")
        @JacksonXmlCData
        private String mediaId;

        public WxImageInfo(String mediaId) {
            this.mediaId = mediaId;
        }
    }

    public WxImageMsgRes(String mediaId) {
        this.image = new WxImageInfo(mediaId);
        this.setMsgType(WechatMsgTypeEnum.IMAGE.getWxFlag());
    }
}