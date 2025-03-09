package cn.fangcai.blog.core.service;

import cn.fangcai.blog.consts.StatusEnum;
import cn.fangcai.blog.core.model.req.paper.QuestionPageReq;
import cn.fangcai.blog.core.model.req.paper.QuestionUptReq;
import cn.fangcai.blog.core.model.res.paper.PaperCateRes;
import cn.fangcai.blog.core.model.res.paper.PaperDetailRes;
import cn.fangcai.blog.core.model.res.paper.PaperListRes;
import cn.fangcai.blog.core.model.res.paper.QuestionRes;
import cn.fangcai.common.model.dto.FcPageRes;

import java.util.List;

/**
 * <p>
 * 试卷表 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2025-03-08
 */
public interface IPaperService {

    List<PaperCateRes> listAllCate();

    List<PaperListRes> listPaperByCate(Integer cateId, StatusEnum status);

    PaperDetailRes getPaperDetail(Integer id, StatusEnum status);

    QuestionRes getQuestion(Integer questionId);

    QuestionRes getQuestionAnswer(Integer questionId);

    QuestionRes getQuestionAnalysis(Integer questionId);

    void incrPaperReadCt(Integer id);

    void incrQuestionReadCt(Integer questionId);


    FcPageRes<QuestionRes> pageQuestion(QuestionPageReq pageReq);

    Boolean uptQuestionName(QuestionUptReq uptReq);


    Boolean uptQuestionIntro(QuestionUptReq uptReq);

    Boolean uptQuestionAnswer(QuestionUptReq uptReq);

    Boolean uptQuestionAnalysis(QuestionUptReq uptReq);

    Integer addQuestion(QuestionUptReq addReq);


}
