package cn.fangcai.starter.log.service;

import cn.fangcai.starter.log.dto.LogRecordDto;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2024/8/19 23:28
 * @description
 */
@Slf4j
public class DefaultLogService implements ILogService {


    @Override
    public Boolean batchSaveLog(List<LogRecordDto> logs) {
        log.warn("Log is not persisted！DefaultLogService batchSaveLog!\n {}", JSONUtil.toJsonStr(logs));
        return Boolean.FALSE;
    }
}