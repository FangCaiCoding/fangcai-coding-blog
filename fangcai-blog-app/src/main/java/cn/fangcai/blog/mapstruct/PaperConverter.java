package cn.fangcai.blog.mapstruct;

import cn.fangcai.blog.core.model.dto.PaperDto;
import cn.fangcai.blog.core.model.entity.Paper;
import cn.fangcai.blog.core.model.entity.PaperCate;
import cn.fangcai.blog.core.model.entity.Question;
import cn.fangcai.blog.core.model.res.paper.PaperCateRes;
import cn.fangcai.blog.core.model.res.paper.PaperDetailRes;
import cn.fangcai.blog.core.model.res.paper.PaperListRes;
import cn.fangcai.blog.core.model.res.paper.QuestionRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:50
 * @description
 */
@Mapper
public interface PaperConverter {
    PaperConverter INSTANCE = Mappers.getMapper(PaperConverter.class);


    List<PaperCateRes> toPaperCateResList(List<PaperCate> paperCateList);

    List<PaperListRes> toPaperResList(List<Paper> papers);

    PaperDetailRes toPaperRes(Paper paper);

    QuestionRes toQuestionRes(Question question);

    PaperDto toPaperDto(Paper paper);
}
