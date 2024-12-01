package cn.fangcai.blog.core.service;

import cn.fangcai.blog.core.model.entity.LogRecord;
import cn.fangcai.blog.core.model.req.LogPageReq;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;

/**
 * <p>
 * 日志记录表 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-12-01
 */
public interface ILogRecordService {

    FcPageRes<LogRecord> page(LogPageReq pageReq);
}
