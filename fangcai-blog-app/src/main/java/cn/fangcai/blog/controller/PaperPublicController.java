package cn.fangcai.blog.controller;

import cn.fangcai.blog.consts.StatusEnum;
import cn.fangcai.blog.core.model.res.paper.PaperCateRes;
import cn.fangcai.blog.core.model.res.paper.PaperDetailRes;
import cn.fangcai.blog.core.model.res.paper.PaperListRes;
import cn.fangcai.blog.core.model.res.paper.QuestionRes;
import cn.fangcai.blog.core.service.IPaperService;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.starter.auth.ano.FcNotCheckLogin;
import cn.fangcai.starter.log.ano.FcLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2025/3/8 12:08
 * @description
 */
@RestController
@RequestMapping("/paper/public")
@Tag(name = "题库相关的API，前端使用")
public class PaperPublicController {

    @Autowired
    private IPaperService paperService;


    @Operation(summary = "获取所有题库分类")
    @GetMapping("/cate/all")
    @FcNotCheckLogin
    public FcResult<List<PaperCateRes>> listAllCate() {
        return FcResult.SUCCESS(paperService.listAllCate());
    }

    @Operation(summary = "获取所有公开的题库-允许cateId为空")
    @GetMapping("/list/all")
    @FcLog(desc = "获取题库列表", actionType = FcLog.ActionType.SELECT)
    @FcNotCheckLogin
    public FcResult<List<PaperListRes>> listPublicAll(@RequestParam(required = false) Integer cateId) {
        return FcResult.SUCCESS(paperService.listPaperByCate(cateId, StatusEnum.PUBLISHED));
    }


    @Operation(summary = "获取题库详情")
    @GetMapping("/detail/{id}")
    @FcNotCheckLogin
    @FcLog(desc = "查询题库详情：%s", respEl = "data.name", actionType = FcLog.ActionType.SELECT)
    public FcResult<PaperDetailRes> getPaperDetail(@PathVariable Integer id) {
        paperService.incrPaperReadCt(id);
        return FcResult.SUCCESS(paperService.getPaperDetail(id, StatusEnum.PUBLISHED));
    }

    @Operation(summary = "获取题目的问题")
    @GetMapping("/question/{questionId}")
    @FcNotCheckLogin
    @FcLog(desc = "查看题目：%s", respEl = "data.name", actionType = FcLog.ActionType.SELECT)
    public FcResult<QuestionRes> getQuestion(@PathVariable Integer questionId) {
        paperService.incrQuestionReadCt(questionId);
        return FcResult.SUCCESS(paperService.getQuestion(questionId));
    }


    @Operation(summary = "获取题目的答案")
    @GetMapping("/question/answer/{questionId}")
    @FcLog(desc = "查看题目答案：%s", respEl = "data.name", actionType = FcLog.ActionType.SELECT)
    public FcResult<QuestionRes> getQuestionAnswer(@PathVariable Integer questionId) {
        return FcResult.SUCCESS(paperService.getQuestionAnswer(questionId));
    }

    @Operation(summary = "获取题目的解析")
    @GetMapping("/question/analysis/{questionId}")
    @FcLog(desc = "查看题目解析：%s", respEl = "data.name", actionType = FcLog.ActionType.SELECT)
    public FcResult<QuestionRes> getQuestionAnalysis(@PathVariable Integer questionId) {
        return FcResult.SUCCESS(paperService.getQuestionAnalysis(questionId));
    }


}
