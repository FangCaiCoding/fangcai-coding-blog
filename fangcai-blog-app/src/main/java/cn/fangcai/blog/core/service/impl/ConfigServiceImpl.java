package cn.fangcai.blog.core.service.impl;

import cn.fangcai.blog.core.model.entity.ConfigWechat;
import cn.fangcai.blog.core.mapper.ConfigWechatMapper;
import cn.fangcai.blog.mapstruct.ConfigConverter;
import cn.fangcai.blog.core.model.req.wx.ConfigWechatSaveReq;
import cn.fangcai.blog.core.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 配置类 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-27
 */
@Service
public class ConfigServiceImpl implements IConfigService {

    @Autowired
    private ConfigWechatRepository wechatRepository;


    @Override
    public Integer saveWechat(ConfigWechatSaveReq wechatSaveReq) {
        ConfigWechat entity = ConfigConverter.INSTANCE.wechatReqToEntity(wechatSaveReq);
        wechatRepository.saveOrUpdate(entity);
        return entity.getId();
    }

    @Override
    public List<ConfigWechat> listWechat(Boolean enabled) {
        return wechatRepository.lambdaQuery()
                .eq(Objects.nonNull(enabled), ConfigWechat::getEnabled, enabled)
                .orderByAsc(ConfigWechat::getOrderNum)
                .orderByAsc(ConfigWechat::getId)
                .list();
    }

    @Repository
    static class ConfigWechatRepository extends ServiceImpl<ConfigWechatMapper, ConfigWechat> {

    }
}
