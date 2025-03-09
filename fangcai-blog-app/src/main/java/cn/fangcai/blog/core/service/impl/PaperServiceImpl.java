package cn.fangcai.blog.core.service.impl;

import cn.fangcai.blog.consts.StatusEnum;
import cn.fangcai.blog.core.mapper.PaperCateMapper;
import cn.fangcai.blog.core.mapper.PaperMapper;
import cn.fangcai.blog.core.mapper.PaperQuestionMapper;
import cn.fangcai.blog.core.mapper.QuestionMapper;
import cn.fangcai.blog.core.model.dto.PaperDto;
import cn.fangcai.blog.core.model.dto.QuestionDto;
import cn.fangcai.blog.core.model.entity.Paper;
import cn.fangcai.blog.core.model.entity.PaperCate;
import cn.fangcai.blog.core.model.entity.PaperQuestion;
import cn.fangcai.blog.core.model.entity.Question;
import cn.fangcai.blog.core.model.req.paper.QuestionPageReq;
import cn.fangcai.blog.core.model.req.paper.QuestionUptReq;
import cn.fangcai.blog.core.model.res.paper.PaperCateRes;
import cn.fangcai.blog.core.model.res.paper.PaperDetailRes;
import cn.fangcai.blog.core.model.res.paper.PaperListRes;
import cn.fangcai.blog.core.model.res.paper.QuestionRes;
import cn.fangcai.blog.core.service.IPaperService;
import cn.fangcai.blog.mapstruct.PaperConverter;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 试卷表 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2025-03-08
 */
@Service
public class PaperServiceImpl implements IPaperService {

    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private PaperCateMapper paperCateMapper;
    @Autowired
    private PaperQuestionMapper paperQuestionMapper;


    @Override
    public List<PaperCateRes> listAllCate() {
        List<PaperCate> paperCates = paperCateMapper.selectList(new LambdaQueryWrapper<PaperCate>()
                .select(PaperCate::getId, PaperCate::getName, PaperCate::getOrderNum)
                .orderByAsc(PaperCate::getOrderNum));
        return PaperConverter.INSTANCE.toPaperCateResList(paperCates);
    }

    @Override
    public List<PaperListRes> listPaperByCate(Integer cateId, StatusEnum status) {
        List<Paper> papers = paperMapper.selectList(new LambdaQueryWrapper<Paper>()
                .eq(status != null, Paper::getStatus, status != null ? status.getCode() : null)
                .eq(cateId != null, Paper::getPaperCateId, cateId)
                .orderByAsc(Paper::getOrderNum)
        );
        if (CollUtil.isEmpty(papers)) {
            return new ArrayList<>();
        }
        List<Integer> cateIdList = papers.stream().map(Paper::getPaperCateId).collect(Collectors.toList());

        List<PaperCate> paperCateList = paperCateMapper.selectList(new LambdaQueryWrapper<PaperCate>()
                .select(PaperCate::getId, PaperCate::getName, PaperCate::getOrderNum)
                .in(PaperCate::getId, cateIdList)
                .orderByAsc(PaperCate::getOrderNum));

        // 按 cateId 分组，并且每个 List<Paper> 按 orderNum 升序排序
        Map<Integer, List<PaperDto>> cateIdAndPaperMap = papers.stream()
                .collect(Collectors.groupingBy(Paper::getPaperCateId,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list -> list.stream()
                                        .map(PaperConverter.INSTANCE::toPaperDto)
                                        .sorted(Comparator.comparing(PaperDto::getOrderNum))
                                        .collect(Collectors.toList())
                        )));
        return paperCateList.stream()
                .map(cate -> {
                    List<PaperDto> paperDtoList = cateIdAndPaperMap.get(cate.getId());

                    PaperListRes paperListRes = new PaperListRes();
                    paperListRes.setCateId(cate.getId());
                    paperListRes.setCateName(cate.getName());
                    paperListRes.setPaperList(paperDtoList);
                    return paperListRes;
                }).collect(Collectors.toList());
    }


    @Override
    public PaperDetailRes getPaperDetail(Integer id, StatusEnum status) {
        Paper paper = paperMapper.selectOne(new LambdaQueryWrapper<Paper>()
                .eq(Paper::getId, id)
                .eq(status != null, Paper::getStatus, status != null ? status.getCode() : null));
        if (paper == null) {
            return null;
        }
        List<PaperQuestion> paperQuestionList = paperQuestionMapper.selectList(new LambdaQueryWrapper<PaperQuestion>()
                .select(PaperQuestion::getQuestionId, PaperQuestion::getQuestionAlias, PaperQuestion::getOrderNum)
                .eq(PaperQuestion::getPaperId, id)
                .orderByAsc(PaperQuestion::getOrderNum)
        );
        PaperDetailRes result = PaperConverter.INSTANCE.toPaperRes(paper);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        result.setQuestionList(questionDtoList);
        if (CollUtil.isEmpty(paperQuestionList)) {
            return result;
        }

        List<Integer> questionIdList = paperQuestionList.stream()
                .map(PaperQuestion::getQuestionId).collect(Collectors.toList());

        Map<Integer, Question> questionIdMap = questionMapper.selectList(new LambdaQueryWrapper<Question>()
                        .select(Question::getId, Question::getName, Question::getReadCt)
                        .in(Question::getId, questionIdList))
                .stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));
        for (PaperQuestion paperQuestion : paperQuestionList) {
            Question question = questionIdMap.get(paperQuestion.getQuestionId());
            if (question == null) {
                continue;
            }
            // 题目别名优先于题目名称
            String nameAlias = StrUtil.blankToDefault(paperQuestion.getQuestionAlias(), question.getName());
            QuestionDto questionDto = new QuestionDto();
            questionDto.setId(paperQuestion.getQuestionId());
            questionDto.setName(question.getName());
            questionDto.setNameAlias(nameAlias);
            questionDto.setReadCt(question.getReadCt());
            questionDtoList.add(questionDto);
        }
        return result;
    }


    @Override
    public QuestionRes getQuestion(Integer questionId) {
        Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>()
                .select(Question::getId, Question::getName, Question::getIntro, Question::getReadCt)
                .eq(Question::getId, questionId));
        return PaperConverter.INSTANCE.toQuestionRes(question);
    }

    @Override
    public QuestionRes getQuestionAnswer(Integer questionId) {
        Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>()
                .select(Question::getId, Question::getName, Question::getAnswer, Question::getReadCt)
                .eq(Question::getId, questionId));
        return PaperConverter.INSTANCE.toQuestionRes(question);
    }

    @Override
    public QuestionRes getQuestionAnalysis(Integer questionId) {
        Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>()
                .select(Question::getId, Question::getName, Question::getAnalysis, Question::getReadCt)
                .eq(Question::getId, questionId));
        return PaperConverter.INSTANCE.toQuestionRes(question);
    }

    @Override
    public void incrPaperReadCt(Integer id) {
        paperMapper.update(new LambdaUpdateWrapper<Paper>()
                .setSql("read_ct = read_ct + 1")
                .eq(Paper::getId, id));
    }

    @Override
    public void incrQuestionReadCt(Integer questionId) {
        questionMapper.update(new LambdaUpdateWrapper<Question>()
                .setSql("read_ct = read_ct + 1")
                .eq(Question::getId, questionId));

    }

    @Override
    public FcPageRes<QuestionRes> pageQuestion(QuestionPageReq pageReq) {
        Page<Question> selectPage = questionMapper.selectPage(new Page<>(pageReq.getPage(), pageReq.getPageSize()),
                new LambdaQueryWrapper<Question>()
                        .like(StrUtil.isNotBlank(pageReq.getName()), Question::getName, pageReq.getName())
                        .orderByDesc(Question::getId));
        List<QuestionRes> questionResList = PaperConverter.INSTANCE.toQuestionResList(selectPage.getRecords());
        return new FcPageRes<QuestionRes>(pageReq)
                .total(selectPage.getTotal())
                .records(questionResList);
    }

    @Override
    public Boolean uptQuestionName(QuestionUptReq uptReq) {
        return questionMapper.update(new LambdaUpdateWrapper<Question>()
                .set(Question::getName, uptReq.getName())
                .eq(Question::getId, uptReq.getId())) > 0;
    }

    @Override
    public Boolean uptQuestionIntro(QuestionUptReq uptReq) {
        return questionMapper.update(new LambdaUpdateWrapper<Question>()
                .set(Question::getIntro, uptReq.getIntro())
                .eq(Question::getId, uptReq.getId())) > 0;
    }

    @Override
    public Boolean uptQuestionAnswer(QuestionUptReq uptReq) {
        return questionMapper.update(new LambdaUpdateWrapper<Question>()
                .set(Question::getAnswer, uptReq.getAnswer())
                .eq(Question::getId, uptReq.getId())) > 0;
    }

    @Override
    public Boolean uptQuestionAnalysis(QuestionUptReq uptReq) {
        return questionMapper.update(new LambdaUpdateWrapper<Question>()
                .set(Question::getAnalysis, uptReq.getAnalysis())
                .eq(Question::getId, uptReq.getId())) > 0;
    }

    @Override
    public Integer addQuestion(QuestionUptReq addReq) {
        Question question = PaperConverter.INSTANCE.toQuestion(addReq);
        questionMapper.insert(question);
        return question.getId();
    }

    @Override
    public Boolean delQuestion(Integer id) {
        return questionMapper.deleteById(id) > 0;
    }
}
