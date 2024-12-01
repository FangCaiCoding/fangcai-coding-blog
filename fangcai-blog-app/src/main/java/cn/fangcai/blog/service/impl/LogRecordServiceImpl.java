package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.mapper.LogRecordMapper;
import cn.fangcai.blog.mapstruct.LogRecordConverter;
import cn.fangcai.blog.model.entity.LogRecord;
import cn.fangcai.blog.service.ILogRecordService;
import cn.fangcai.starter.log.dto.LogRecordDto;
import cn.fangcai.starter.log.service.IFcLogService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return logRecordServiceRepository.saveBatch(LogRecordConverter.INSTANCE.dtoListToEntityList(logList));
    }

    @Component
    static class LogRecordServiceRepository extends ServiceImpl<LogRecordMapper, LogRecord> {

    }
}
