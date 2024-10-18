package cn.fangcai.blog.mapstruct;

import cn.fangcai.blog.model.entity.Website;
import cn.fangcai.blog.model.req.website.WebsiteSaveReq;
import cn.fangcai.blog.model.res.website.WebsiteRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:50
 * @description
 */
@Mapper
public interface WebSiteConverter {
    WebSiteConverter INSTANCE = Mappers.getMapper(WebSiteConverter.class);


    Website savReqToEntity(WebsiteSaveReq saveReq);

    WebsiteRes entityToListRes(Website website);
}
