package cn.fangcai.blog.core.service;

import cn.fangcai.blog.core.model.entity.VipToken;
import cn.fangcai.blog.core.model.entity.VipExchangeRecord;

import java.util.List;

/**
 * <p>
 * VIP兑换Token表 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2025-04-06
 */
public interface IVipTokenService {

    /**
     * 根据Token值查询VIP兑换Token
     *
     * @param tokenValue Token值
     * @return VipToken
     */
    VipToken getByTokenValue(String tokenValue);

    /**
     * 验证Token是否有效
     *
     * @param tokenValue Token值
     * @return 是否有效
     */
    boolean validateToken(String tokenValue);

    /**
     * 兑换VIP
     *
     * @param userId     用户ID
     * @param tokenValue Token值
     * @return 兑换结果
     */
    boolean exchangeVip(Integer userId, String tokenValue);

    /**
     * 获取用户的VIP兑换记录
     *
     * @param userId 用户ID
     * @return 兑换记录列表
     */
    List<VipExchangeRecord> getUserExchangeRecords(Integer userId);
}