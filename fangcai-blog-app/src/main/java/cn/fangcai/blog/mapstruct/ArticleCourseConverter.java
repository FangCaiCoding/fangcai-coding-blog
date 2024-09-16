package cn.fangcai.blog.mapstruct;

import cn.fangcai.blog.model.entity.ArticleCourse;
import cn.fangcai.blog.model.entity.CourseDetail;
import cn.fangcai.blog.model.req.CourseDetailSaveReq;
import cn.fangcai.blog.model.req.CourseSaveReq;
import cn.fangcai.blog.model.res.CourseDetailRes;
import cn.fangcai.blog.model.res.CourseRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:50
 * @description
 */
@Mapper
public interface ArticleCourseConverter {
    ArticleCourseConverter INSTANCE = Mappers.getMapper(ArticleCourseConverter.class);


    ArticleCourse toArticleCourse(CourseSaveReq saveReq);

    CourseRes toCourseRes(ArticleCourse articleCourse);

    CourseDetailRes toCourseDetailRes(CourseDetail courseDetail);

    List<CourseRes> toCourseResList(List<ArticleCourse> records);

    CourseDetail toCourseDetail(CourseDetailSaveReq saveReq);
}
