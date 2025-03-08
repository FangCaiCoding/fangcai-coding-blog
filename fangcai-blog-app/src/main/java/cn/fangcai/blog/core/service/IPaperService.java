package cn.fangcai.blog.core.service;

import cn.fangcai.blog.consts.StatusEnum;
import cn.fangcai.blog.core.model.res.paper.PaperCateRes;
import cn.fangcai.blog.core.model.res.paper.PaperDetailRes;
import cn.fangcai.blog.core.model.res.paper.PaperListRes;
import cn.fangcai.blog.core.model.res.paper.QuestionRes;

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
}
