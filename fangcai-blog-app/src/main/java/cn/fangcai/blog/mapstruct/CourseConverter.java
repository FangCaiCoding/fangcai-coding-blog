package cn.fangcai.blog.mapstruct;

import cn.fangcai.blog.model.entity.Course;
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
public interface CourseConverter {
    CourseConverter INSTANCE = Mappers.getMapper(CourseConverter.class);


    Course toCourse(CourseSaveReq saveReq);

    CourseRes toCourseRes(Course Course);

    CourseDetailRes toCourseDetailRes(CourseDetail courseDetail);

    List<CourseRes> toCourseResList(List<Course> records);

    CourseDetail toCourseDetail(CourseDetailSaveReq saveReq);
    List<CourseDetail> toCourseDetailList(List<CourseDetailSaveReq> saveReqList);
}
