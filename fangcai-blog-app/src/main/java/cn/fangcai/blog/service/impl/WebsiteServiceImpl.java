package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.consts.BlogErrorCodeEnum;
import cn.fangcai.blog.consts.StatusEnum;
import cn.fangcai.blog.mapper.WebsiteCateMapper;
import cn.fangcai.blog.mapper.WebsiteMapper;
import cn.fangcai.blog.mapstruct.WebSiteConverter;
import cn.fangcai.blog.model.entity.Website;
import cn.fangcai.blog.model.entity.WebsiteCate;
import cn.fangcai.blog.model.req.website.WebsiteSaveReq;
import cn.fangcai.blog.model.res.website.WebsiteListRes;
import cn.fangcai.blog.model.res.website.WebsiteRes;
import cn.fangcai.blog.service.IWebsiteService;
import cn.fangcai.common.model.exception.FcBusinessException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
    public List<WebsiteListRes> listPublicAll() {
        List<WebsiteRes> webSiteList = websiteRepository.lambdaQuery()
                .eq(Website::getStatus, StatusEnum.PUBLISHED.getCode())
                .list()
                .stream()
                .map(WebSiteConverter.INSTANCE::entityToListRes)
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


    /**
     *  判断分类是否存在
     * @param cateId 分类ID
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