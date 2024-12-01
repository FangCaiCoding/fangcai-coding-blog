package cn.fangcai.starter.log.aop;

import cn.fangcai.common.model.uitls.SpringMVCUtil;
import cn.fangcai.starter.auth.FcAuthContext;
import cn.fangcai.starter.auth.FcClientContext;
import cn.fangcai.starter.log.ano.FcLog;
import cn.fangcai.starter.log.dto.LogRecordDto;
import cn.fangcai.starter.log.service.IFcLogService;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author MouFangCai
 * @date 2024/11/29 22:33
 * @description 日志记录切面
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Resource
    private IFcLogService fcLogService;

    /**
     * 排除敏感属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    /**
     * 计算操作消耗时间
     */
    private static final ThreadLocal<LocalDateTime> TIME_THREADLOCAL = new NamedThreadLocal<LocalDateTime>("Cost Time");

    /**
     * 处理请求前执行
     */
    @Before(value = "@annotation(fcLog)")
    public void boBefore(JoinPoint joinPoint, FcLog fcLog) {
        TIME_THREADLOCAL.set(LocalDateTime.now());
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(fcLog)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, FcLog fcLog, Object result) {
        handleLog(joinPoint, fcLog, null, result);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(fcLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, FcLog fcLog, Exception e) {
        handleLog(joinPoint, fcLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, FcLog fcLog, final Exception e, Object result) {
        try {
            LogRecordDto logRecord = this.initByFcLog(joinPoint, fcLog, result);
            logRecord.setSuccess(true);
            if (e != null) {
                logRecord.setSuccess(false);
                logRecord.setErrorMsg(StrUtil.subPre(e.getMessage(), 1024));
            }
            LocalDateTime startTime = TIME_THREADLOCAL.get();
            logRecord.setOperateTime(startTime);
            long costTime = LocalDateTimeUtil.between(startTime, LocalDateTime.now(), ChronoUnit.MILLIS);
            logRecord.setCostTime(costTime);
            // 保存数据库,后续可优化为异步+缓冲池+批量入库
            fcLogService.batchSaveLog(List.of(logRecord));
        } catch (Exception logError) {
            log.error("handleLog Error!", logError);
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint
     * @param fcLog     日志
     * @param result    接口响应报文
     *
     * @throws Exception
     */
    private LogRecordDto initByFcLog(JoinPoint joinPoint,
                                     FcLog fcLog,
                                     Object result) {
        LogRecordDto logRecord = new LogRecordDto();
        logRecord.setUserId(FcAuthContext.getUserIdAsStr());
        logRecord.setClientId(FcClientContext.getClientId());
        logRecord.setClientIp(FcClientContext.getClientIp());
        logRecord.setLogDesc(fcLog.desc());
        logRecord.setActionType(fcLog.actionType().name());
        logRecord.setBusinessFlag(fcLog.businessFlag());

        logRecord.setReqUri(SpringMVCUtil.getRequest().getRequestURI());
        logRecord.setReqMethod(SpringMVCUtil.getRequest().getMethod());
        if (fcLog.isSaveReqData()) {
            Object[] args = joinPoint.getArgs();
            logRecord.setReqData(StrUtil.subPre(JSONUtil.toJsonStr(args), 512));
        }
        if (fcLog.isSaveRespData()) {
            logRecord.setReqData(StrUtil.subPre(JSONUtil.toJsonStr(result), 512));
        }
        return logRecord;
    }

}
