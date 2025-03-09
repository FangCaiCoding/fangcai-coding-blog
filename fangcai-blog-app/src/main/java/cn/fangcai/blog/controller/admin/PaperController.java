package cn.fangcai.blog.controller.admin;

import cn.fangcai.blog.consts.StatusEnum;
import cn.fangcai.blog.core.model.req.paper.QuestionPageReq;
import cn.fangcai.blog.core.model.req.paper.QuestionUptReq;
import cn.fangcai.blog.core.model.res.paper.PaperCateRes;
import cn.fangcai.blog.core.model.res.paper.PaperDetailRes;
import cn.fangcai.blog.core.model.res.paper.PaperListRes;
import cn.fangcai.blog.core.model.res.paper.QuestionRes;
import cn.fangcai.blog.core.service.IPaperService;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.common.model.valider.EditGroup;
import cn.fangcai.starter.auth.ano.FcCheckAuth;
import cn.fangcai.starter.auth.ano.FcNotCheckLogin;
import cn.fangcai.starter.log.ano.FcLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2025/3/8 12:08
 * @description
 */
@RestController
@RequestMapping("/paper")
@Tag(name = "题库相关的API，后台管理")
public class PaperController {

    @Autowired
    private IPaperService paperService;


    @Operation(summary = "获取所有的题库-允许cateId为空")
    @GetMapping("/list/all")
    @FcCheckAuth(values = {"paper:query"})
    public FcResult<List<PaperListRes>> listAllByCate(@RequestParam(required = false) Integer cateId) {
        return FcResult.SUCCESS(paperService.listPaperByCate(cateId,null));
    }

    @Operation(summary = "获取题库详情")
    @GetMapping("/detail/{id}")
    @FcCheckAuth(values = {"paper:query"})
    public FcResult<PaperDetailRes> getPaperDetail(@PathVariable Integer id) {
        return FcResult.SUCCESS(paperService.getPaperDetail(id, null));
    }

    @Operation(summary = "获取题目的列表")
    @PostMapping("/question/page")
    @FcCheckAuth(values = {"question:query"})
    public FcResult<FcPageRes<QuestionRes>> pageQuestion(@RequestBody @Validated QuestionPageReq pageReq) {
        return FcResult.SUCCESS(paperService.pageQuestion(pageReq));
    }

    @Operation(summary = "新增题目")
    @PostMapping("/question")
    @FcCheckAuth(values = {"question:add"})
    public FcResult<Integer> addQuestion(@RequestBody @Validated QuestionUptReq addReq) {
        return FcResult.SUCCESS(paperService.addQuestion(addReq));
    }


    @Operation(summary = "删除题目")
    @DeleteMapping("/question/{id}")
    @FcCheckAuth(values = {"question:edit"})
    public FcResult<Boolean> delQuestion(@PathVariable Integer id) {
        return FcResult.SUCCESS(paperService.delQuestion(id));
    }


    @Operation(summary = "修改问题的名称")
    @PutMapping("/question/name")
    @FcCheckAuth(values = {"question:edit"})
    public FcResult<Boolean> uptQuestionName(@RequestBody @Validated(EditGroup.class) QuestionUptReq uptReq){
        return FcResult.SUCCESS(paperService.uptQuestionName(uptReq));
    }

    @Operation(summary = "修改问题的描述")
    @PutMapping("/question/intro")
    @FcCheckAuth(values = {"question:edit"})
    public FcResult<Boolean> uptQuestionIntro(@RequestBody @Validated(EditGroup.class) QuestionUptReq uptReq){
        return FcResult.SUCCESS(paperService.uptQuestionIntro(uptReq));
    }

    @Operation(summary = "修改问题的答案")
    @PutMapping("/question/answer")
    @FcCheckAuth(values = {"question:edit"})
    public FcResult<Boolean> uptQuestionAnswer(@RequestBody @Validated(EditGroup.class) QuestionUptReq uptReq){
        return FcResult.SUCCESS(paperService.uptQuestionAnswer(uptReq));
    }


    @Operation(summary = "修改问题的解析")
    @PutMapping("/question/analysis")
    @FcCheckAuth(values = {"question:edit"})
    public FcResult<Boolean> uptQuestionAnalysis(@RequestBody @Validated(EditGroup.class) QuestionUptReq uptReq){
        return FcResult.SUCCESS(paperService.uptQuestionAnalysis(uptReq));
    }


}
