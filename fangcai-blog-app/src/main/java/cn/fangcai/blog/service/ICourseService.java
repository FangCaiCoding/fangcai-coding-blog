package cn.fangcai.blog.service;

import cn.fangcai.blog.model.req.CourseDetailSaveReq;
import cn.fangcai.blog.model.req.CoursePageReq;
import cn.fangcai.blog.model.req.CourseSaveReq;
import cn.fangcai.blog.model.res.CourseRes;
import cn.fangcai.common.model.dto.FcPageRes;

/**
 * <p>
 * 文章教程 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-16
 */
public interface ICourseService {


    Integer addCourse(CourseSaveReq saveReq);

    Integer editCourse(CourseSaveReq editReq);

    CourseRes getById(Integer id);


    FcPageRes<CourseRes> pageCourse(CoursePageReq pageReq);

    Boolean delCourse(Integer id);

    Boolean uptOrderNum(Integer id, Integer orderNum);


    Integer addCourseDetail(CourseDetailSaveReq saveReq);

    Integer editCourseDetail(CourseDetailSaveReq editReq);

    Boolean delCourseDetail(Integer detailId);


}
