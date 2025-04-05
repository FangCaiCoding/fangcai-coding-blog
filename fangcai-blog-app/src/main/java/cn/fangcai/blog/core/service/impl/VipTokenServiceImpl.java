package cn.fangcai.blog.core.service.impl;

import cn.fangcai.blog.consts.BlogErrorCodeEnum;
import cn.fangcai.blog.core.mapper.VipTokenMapper;
import cn.fangcai.blog.core.model.entity.User;
import cn.fangcai.blog.core.model.entity.VipExchangeRecord;
import cn.fangcai.blog.core.model.entity.VipToken;
import cn.fangcai.blog.core.service.IVipTokenService;
import cn.fangcai.common.model.exception.FcBusinessException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * VIP兑换Token表 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2025-04-06
 */
@Service
@Slf4j
public class VipTokenServiceImpl implements IVipTokenService {

    @Autowired
    private VipTokenRepository vipTokenRepository;
    
    @Autowired
    private VipExchangeRecordRepository vipExchangeRecordRepository;
    
    @Autowired
    private UserServiceImpl.UserRepository userRepository;

    @Override
    public VipToken getByTokenValue(String tokenValue) {
        return vipTokenRepository.lambdaQuery()
                .eq(VipToken::getTokenValue, tokenValue)
                .one();
    }

    @Override
    public boolean validateToken(String tokenValue) {
        VipToken vipToken = this.getByTokenValue(tokenValue);
        if (vipToken == null) {
            return false;
        }
        
        // 检查Token是否有效
        if (!vipToken.getValid()) {
            return false;
        }
        
        // 检查Token是否过期
        if (vipToken.getExpireTime() != null && vipToken.getExpireTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        
        // 检查Token是否已达到兑换次数上限
        return vipToken.getExchangeCount() < vipToken.getExchangeLimit();
    }

    @Override
    public boolean exchangeVip(Integer userId, String tokenValue) {
        // 验证Token
        if (!validateToken(tokenValue)) {
            throw new FcBusinessException(BlogErrorCodeEnum.INVALID_VIP_TOKEN);
        }
        
        // 获取Token信息
        VipToken vipToken = this.getByTokenValue(tokenValue);

        // 检查用户是否已经兑换过
        boolean exists = vipExchangeRecordRepository.exists(new LambdaQueryWrapper<VipExchangeRecord>()
                .eq(VipExchangeRecord::getUserId, userId)
                .eq(VipExchangeRecord::getTokenId, vipToken.getId()));
        if (exists) {
            throw new FcBusinessException(BlogErrorCodeEnum.VIP_EXCHANGE_LIMIT);
        }

        // 获取用户信息
        User user = userRepository.getById(userId);
        // 计算VIP到期时间
        LocalDateTime vipStartTime = LocalDateTime.now();
        LocalDateTime vipEndTime;
        
        // 如果用户已经是VIP，则在原有VIP到期时间基础上增加天数
        if (user.getVipEndTime() != null && user.getVipEndTime().isAfter(LocalDateTime.now())) {
            vipEndTime = user.getVipEndTime().plusDays(vipToken.getVipDays());
        } else {
            // 否则从当前时间开始计算
            vipEndTime = vipStartTime.plusDays(vipToken.getVipDays());
        }

        // 更新Token使用次数，作为兜底，防止出现超出兑换次数限制的情况
        boolean exchange = vipTokenRepository.lambdaUpdate()
                .setIncrBy(VipToken::getExchangeCount, 1)
                .eq(VipToken::getId, vipToken.getId())
                .ltSql(VipToken::getExchangeCount, "exchange_limit")
                .ge(VipToken::getExpireTime, LocalDateTime.now())
                .update();

        if (!exchange) {
            throw new FcBusinessException(BlogErrorCodeEnum.INVALID_VIP_TOKEN);
        }

        // 记录兑换记录
        VipExchangeRecord record = new VipExchangeRecord();
        record.setUserId(userId);
        record.setTokenId(vipToken.getId());
        record.setOldVipEndTime(user.getVipEndTime());
        record.setVipEndTime(vipEndTime);
        vipExchangeRecordRepository.save(record);

        // 更新用户VIP到期时间
        userRepository.lambdaUpdate()
                .eq(User::getId, userId)
                .set(User::getVipEndTime, vipEndTime)
                .update();
        return true;
    }

    @Override
    public List<VipExchangeRecord> getUserExchangeRecords(Integer userId) {
        return vipExchangeRecordRepository.lambdaQuery()
                .eq(VipExchangeRecord::getUserId, userId)
                .orderByDesc(VipExchangeRecord::getCreateTime)
                .list();
    }
    
    @Repository
    static class VipTokenRepository extends ServiceImpl<VipTokenMapper, VipToken> {
    }
    
    @Repository
    static class VipExchangeRecordRepository extends ServiceImpl<cn.fangcai.blog.core.mapper.VipExchangeRecordMapper, VipExchangeRecord> {
    }
}