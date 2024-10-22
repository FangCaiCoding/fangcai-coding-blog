package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.consts.BlogErrorCodeEnum;
import cn.fangcai.blog.consts.StatusEnum;
import cn.fangcai.blog.mapper.WebsiteCateMapper;
import cn.fangcai.blog.mapper.WebsiteMapper;
import cn.fangcai.blog.mapstruct.WebSiteConverter;
import cn.fangcai.blog.model.entity.Website;
import cn.fangcai.blog.model.entity.WebsiteCate;
import cn.fangcai.blog.model.req.website.WebSitePageReq;
import cn.fangcai.blog.model.req.website.WebsiteSaveReq;
import cn.fangcai.blog.model.res.website.WebsiteCateRes;
import cn.fangcai.blog.model.res.website.WebsiteListRes;
import cn.fangcai.blog.model.res.website.WebsiteRes;
import cn.fangcai.blog.service.IWebsiteService;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源站点信息 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-18
 */
@Service
public class WebsiteServiceImpl implements IWebsiteService {

    @Autowired
    private WebsiteRepository websiteRepository;
    @Autowired
    private WebsiteCateRepository websiteCateRepository;

    @Override
    public Integer save(WebsiteSaveReq saveReq) {
        // TODO : by mfc on 2024/10/18 权限控制待实现
        if (!cateExists(saveReq.getCateId())) {
            throw new FcBusinessException(BlogErrorCodeEnum.WEB_SITE_CATE_NOT_FOUND);
        }
        Website website = WebSiteConverter.INSTANCE.savReqToEntity(saveReq);
        websiteRepository.saveOrUpdate(website);
        return website.getId();
    }

    @Override
    public Boolean uptStatus(Integer id, Byte status) {
        // TODO : by mfc on 2024/10/18 权限控制待实现
        return websiteRepository.lambdaUpdate()
                .set(Website::getStatus, status)
                .eq(Website::getId, id)
                .update();
    }

    @Override
    public Boolean delete(Integer id) {
        return websiteRepository.removeById(id);
    }


    @Override
    public FcPageRes<WebsiteRes> pageByReq(WebSitePageReq pageReq) {
        Page<Website> page = websiteRepository.lambdaQuery()
                .eq(Objects.nonNull(pageReq.getStatus()), Website::getStatus, pageReq.getStatus())
                .like(StrUtil.isNotBlank(pageReq.getTitle()), Website::getTitle, pageReq.getTitle())
                .eq(Objects.nonNull(pageReq.getCateId()), Website::getCateId, pageReq.getCateId())
                .orderByAsc(Website::getOrderNum)
                .orderByDesc(Website::getCreateTime, Website::getId)
                .page(new Page<>(pageReq.getPage(), pageReq.getPageSize()));

        List<WebsiteRes> websiteRes = WebSiteConverter.INSTANCE.entityToListRes(page.getRecords());
        List<Integer> cateIdList = websiteRes.stream().map(WebsiteRes::getCateId).toList();
        if (!cateIdList.isEmpty()) {
            Map<Integer, String> cateIdAndNameMap = websiteCateRepository.listByIds(cateIdList).stream().collect(Collectors.toMap(WebsiteCate::getId, WebsiteCate::getName));
            websiteRes.forEach(website -> website.setCateName(cateIdAndNameMap.get(website.getCateId())));
        }
        return new FcPageRes<WebsiteRes>(pageReq)
                .total(page.getTotal())
                .records(websiteRes);
    }


    @Override
    public List<WebsiteListRes> listPublicAll() {
        List<WebsiteRes> webSiteList = websiteRepository.lambdaQuery()
                .eq(Website::getStatus, StatusEnum.PUBLISHED.getCode())
                .list()
                .stream()
                .map(WebSiteConverter.INSTANCE::entityToRes)
                .toList();
        if (!webSiteList.isEmpty()) {
            List<Integer> cateIdList = webSiteList.stream().map(WebsiteRes::getCateId).toList();
            List<WebsiteCate> cateList = websiteCateRepository.lambdaQuery()
                    .in(WebsiteCate::getId, cateIdList)
                    .orderByAsc(WebsiteCate::getOrderNum)
                    .list();

            // 按 cateId 分组，并且每个 List<WebsiteRes> 按 orderNum 升序排序
            Map<Integer, List<WebsiteRes>> cateIdAndSiteMap = webSiteList.stream()
                    .collect(Collectors.groupingBy(WebsiteRes::getCateId,
                            Collectors.collectingAndThen(Collectors.toList(),
                                    list -> list.stream()
                                            .sorted(Comparator.comparing(WebsiteRes::getOrderNum))
                                            .collect(Collectors.toList())
                            )));

            return cateList.stream()
                    .map(cate -> {
                        WebsiteListRes res = new WebsiteListRes();
                        res.setCateId(cate.getId());
                        res.setCateName(cate.getName());
                        res.setWebsiteList(cateIdAndSiteMap.get(cate.getId()));
                        return res;
                    })
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


    @Override
    public List<WebsiteCateRes> listAllCate() {
        return websiteCateRepository.lambdaQuery()
                .orderByAsc(WebsiteCate::getOrderNum)
                .orderByDesc(WebsiteCate::getCreateTime, WebsiteCate::getId)
                .list().stream()
                .map(WebSiteConverter.INSTANCE::entityToCateRes)
                .toList();
    }


    /**
     * 点击网站,阅读数+1
     *
     * @param id
     *
     * @return
     */
    @Override
    public Boolean clickWebsite(Integer id) {
        return websiteRepository.lambdaUpdate()
                .setSql("read_ct = read_ct + 1")
                .eq(Website::getId, id)
                .update();
    }

    /**
     * 判断分类是否存在
     *
     * @param cateId 分类ID
     *
     * @return
     */
    private Boolean cateExists(Integer cateId) {
        return websiteCateRepository.lambdaQuery().eq(WebsiteCate::getId, cateId).exists();
    }

    @Component
    static class WebsiteRepository extends ServiceImpl<WebsiteMapper, Website> {
    }

    @Component
    static class WebsiteCateRepository extends ServiceImpl<WebsiteCateMapper, WebsiteCate> {
    }

}
