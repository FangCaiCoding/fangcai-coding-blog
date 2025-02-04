package cn.fangcai.blog.core.service;

import cn.fangcai.blog.core.model.req.website.WebSitePageReq;
import cn.fangcai.blog.core.model.req.website.WebsiteSaveReq;
import cn.fangcai.blog.core.model.res.website.WebsiteCateRes;
import cn.fangcai.blog.core.model.res.website.WebsiteListRes;
import cn.fangcai.blog.core.model.res.website.WebsiteRes;
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
public interface IWebsiteService {

    /**
     * 保存站点信息
     *
     * @param saveReq 站点信息
     *
     * @return 站点唯一标识
     */
    Integer save(WebsiteSaveReq saveReq);



    /**
     * 更新站点状态
     *
     * @param id 站点唯一标识
     * @param status 站点状态
     *
     * @return 是否更新成功
     */
    Boolean uptStatus(Integer id, Byte status);

    /**
     * 删除站点信息(逻辑删除)
     *
     * @param id 站点唯一标识
     *
     * @return 是否删除成功
     */
    Boolean delete(Integer id);

    /**
     * 获取所有站点信息
     *
     * @return 站点信息列表
     */

    List<WebsiteListRes> listPublicAll();

    /**
     * 根据条件分页查询站点信息
     *
     * @param pageReq 分页查询条件
     *
     * @return 分页查询结果
     */
    FcPageRes<WebsiteRes> pageByReq(WebSitePageReq pageReq);

    /**
     * 获取所有站点分类信息
     *
     * @return 站点分类信息列表
     */
    List<WebsiteCateRes> listAllCate();

    /**
     * 点击站点
     *
     * @param id 站点唯一标识
     *
     * @return 是否点击成功
     */
    Boolean clickWebsite(Integer id);
}
