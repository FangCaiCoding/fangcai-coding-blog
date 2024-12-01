package cn.fangcai.blog.core.service;

import cn.fangcai.blog.core.model.entity.ConfigWechat;
import cn.fangcai.blog.core.model.req.wx.ConfigWechatSaveReq;

import java.util.List;

/**
 * <p>
 * 配置类 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-27
 */
public interface IConfigService {

    Integer saveWechat(ConfigWechatSaveReq wechatSaveReq);

    List<ConfigWechat> listWechat(Boolean enabled);
}
