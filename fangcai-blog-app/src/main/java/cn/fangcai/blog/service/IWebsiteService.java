package cn.fangcai.blog.service;

import cn.fangcai.blog.model.req.website.WebSitePageReq;
import cn.fangcai.blog.model.req.website.WebsiteSaveReq;
import cn.fangcai.blog.model.res.website.WebsiteCateRes;
import cn.fangcai.blog.model.res.website.WebsiteListRes;
import cn.fangcai.blog.model.res.website.WebsiteRes;
import cn.fangcai.common.model.dto.FcPageRes;

import java.util.List;

/**
 * <p>
 * 资源站点信息 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-18
 */
public interface IWebsiteService{

    Integer save(WebsiteSaveReq saveReq);


    Boolean uptStatus(Integer id, Byte status);

    Boolean delete(Integer id);
    

    List<WebsiteListRes> listPublicAll();


    FcPageRes<WebsiteRes> pageByReq(WebSitePageReq pageReq);

    List<WebsiteCateRes> listAllCate();

    Boolean clickWebsite(Integer id);
}
