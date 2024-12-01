package cn.fangcai.blog.mapstruct;

import cn.fangcai.blog.core.model.entity.ConfigWechat;
import cn.fangcai.blog.core.model.req.wx.ConfigWechatSaveReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:50
 * @description
 */
@Mapper
public interface ConfigConverter {
    ConfigConverter INSTANCE = Mappers.getMapper(ConfigConverter.class);


    ConfigWechat wechatReqToEntity(ConfigWechatSaveReq wechatSaveReq);
}
