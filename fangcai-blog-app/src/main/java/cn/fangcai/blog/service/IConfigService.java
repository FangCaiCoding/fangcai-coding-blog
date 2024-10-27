package cn.fangcai.blog.service;

import cn.fangcai.blog.model.entity.ConfigWechat;
import cn.fangcai.blog.model.req.wx.ConfigPageReq;
import cn.fangcai.blog.model.req.wx.ConfigWechatSaveReq;
import cn.fangcai.common.model.dto.FcPageRes;

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
