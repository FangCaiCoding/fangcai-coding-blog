package cn.fangcai.blog.service;

import cn.fangcai.blog.model.req.CourseDetailSaveReq;
import cn.fangcai.blog.model.req.CoursePageReq;
import cn.fangcai.blog.model.req.CourseSaveReq;
import cn.fangcai.blog.model.res.CourseRes;
import cn.fangcai.common.model.dto.FcPageRes;

import java.util.List;
import java.util.Map;

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

    Boolean incrReadCt(Integer id);

    FcPageRes<CourseRes> pageCourse(CoursePageReq pageReq);

    Boolean delCourse(Integer id);

    Boolean uptOrderNum(Integer id, Integer orderNum);

    Boolean addCourseDetail(List<CourseDetailSaveReq> saveReqList);

    Integer editCourseDetail(CourseDetailSaveReq editReq);

    Boolean delCourseDetail(List<Integer> detailIds);


    /**
     * 根据文章id列表，获取文章id和随机的课程id的map
     *
     * @param articleIdList
     *
     * @return Map<文章id, 随机课程id>
     */
    Map<Integer, Integer> getMapByArticleIds(List<Integer> articleIdList);

    /**
     * 根据课程id获取文章id列表
     *
     * @param courseId
     *
     * @return
     */
    List<Integer> listArticleIdById(Integer courseId);
}
