package cn.fangcai.blog.controller.admin;

import cn.fangcai.blog.core.model.entity.LogRecord;
import cn.fangcai.blog.core.model.req.LogPageReq;
import cn.fangcai.blog.core.service.ILogRecordService;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.starter.auth.ano.FcCheckAuth;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 日志记录表 前端控制器
 * </p>
 *
 * @author MouFangCai
 * @since 2024-12-01
 */
@RestController
@RequestMapping("/log")
public class LogRecordController {

    @Autowired
    private ILogRecordService logRecordService;

    @Operation(summary = "分页查询日志记录")
    @PostMapping("/page")
    @FcCheckAuth(values = "logRecord:page")
    public FcResult<FcPageRes<LogRecord>> page(@RequestBody @Validated LogPageReq pageReq) {
        return FcResult.SUCCESS(logRecordService.page(pageReq));
    }
}
