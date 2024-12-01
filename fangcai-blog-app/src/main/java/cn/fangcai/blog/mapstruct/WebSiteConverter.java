package cn.fangcai.blog.mapstruct;

import cn.fangcai.blog.core.model.entity.Website;
import cn.fangcai.blog.core.model.entity.WebsiteCate;
import cn.fangcai.blog.core.model.req.website.WebsiteSaveReq;
import cn.fangcai.blog.core.model.res.website.WebsiteCateRes;
import cn.fangcai.blog.core.model.res.website.WebsiteRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:50
 * @description
 */
@Mapper
public interface WebSiteConverter {
    WebSiteConverter INSTANCE = Mappers.getMapper(WebSiteConverter.class);


    Website savReqToEntity(WebsiteSaveReq saveReq);

    WebsiteRes entityToRes(Website website);

    List<WebsiteRes> entityToListRes(List<Website> websites);

    @Mapping(target = "cateId", source = "cate.id")
    WebsiteCateRes entityToCateRes(WebsiteCate cate);
}
