package cn.fangcai.blog.core.service.impl;

import cn.fangcai.blog.core.mapper.LogRecordMapper;
import cn.fangcai.blog.core.model.entity.LogRecord;
import cn.fangcai.blog.core.model.req.LogPageReq;
import cn.fangcai.blog.core.service.ILogRecordService;
import cn.fangcai.blog.mapstruct.LogRecordConverter;
import cn.fangcai.blog.uitls.IpRegionUtil;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.starter.log.dto.LogRecordDto;
import cn.fangcai.starter.log.service.IFcLogService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 日志记录表 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-12-01
 */
@Service
public class LogRecordServiceImpl implements ILogRecordService, IFcLogService {

    @Autowired
    private LogRecordServiceRepository logRecordServiceRepository;

    @Override
    public Boolean batchSaveLog(List<LogRecordDto> logList) {
        if (CollUtil.isEmpty(logList)) {
            return Boolean.FALSE;
        }
        List<LogRecord> logEntityList = LogRecordConverter.INSTANCE.dtoListToEntityList(logList);
        for (LogRecord logRecord : logEntityList) {
            logRecord.setIpAddress(IpRegionUtil.getRegion(logRecord.getClientIp()));
        }
        return logRecordServiceRepository.saveBatch(logEntityList);
    }

    @Override
    public FcPageRes<LogRecord> page(LogPageReq pageReq) {
        Page<LogRecord> page = logRecordServiceRepository.lambdaQuery()
                .eq(StrUtil.isNotBlank(pageReq.getUserId()), LogRecord::getUserId, pageReq.getUserId())
                .eq(StrUtil.isNotBlank(pageReq.getClientId()), LogRecord::getClientId, pageReq.getClientId())
                .eq(StrUtil.isNotBlank(pageReq.getLogDesc()), LogRecord::getLogDesc, pageReq.getLogDesc())
                .ge(Objects.nonNull(pageReq.getStartOperateTime()), LogRecord::getOperateTime, pageReq.getStartOperateTime())
                .le(Objects.nonNull(pageReq.getEndOperateTime()), LogRecord::getOperateTime, pageReq.getEndOperateTime())
                .orderByDesc(LogRecord::getOperateTime, LogRecord::getId)
                .page(new Page<>(pageReq.getPage(), pageReq.getPageSize()));
        return new FcPageRes<LogRecord>(pageReq)
                .total(page.getTotal())
                .records(page.getRecords());
    }

    @Component
    static class LogRecordServiceRepository extends ServiceImpl<LogRecordMapper, LogRecord> {

    }
}
