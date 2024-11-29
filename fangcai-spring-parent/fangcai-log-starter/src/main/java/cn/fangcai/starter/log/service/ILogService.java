package cn.fangcai.starter.log.service;

import cn.fangcai.starter.log.dto.LogRecordDto;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2024/8/19 23:28
 * @description
 */
public interface ILogService {


    /**
     * 批量保存日志
     * @param logs
     * @return
     */
    Boolean batchSaveLog(List<LogRecordDto> logs);

}
