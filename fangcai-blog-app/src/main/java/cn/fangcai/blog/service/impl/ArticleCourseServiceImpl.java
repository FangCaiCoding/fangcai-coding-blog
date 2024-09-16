package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.mapper.ArticleCourseMapper;
import cn.fangcai.blog.mapper.CourseDetailMapper;
import cn.fangcai.blog.mapstruct.ArticleCourseConverter;
import cn.fangcai.blog.model.entity.ArticleCourse;
import cn.fangcai.blog.model.entity.CourseDetail;
import cn.fangcai.blog.model.req.CourseDetailSaveReq;
import cn.fangcai.blog.model.req.CoursePageReq;
import cn.fangcai.blog.model.req.CourseSaveReq;
import cn.fangcai.blog.model.res.CourseDetailRes;
import cn.fangcai.blog.model.res.CourseRes;
import cn.fangcai.blog.service.IArticleCourseService;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.enums.FcErrorCodeEnum;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章教程 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-16
 */
@Service
public class ArticleCourseServiceImpl implements IArticleCourseService {

    @Autowired
    private ArticleCourseRepository courseRepository;

    @Autowired
    private CourseDetailRepository courseDetailRepository;


    @Override
    public Integer addCourse(CourseSaveReq saveReq) {
        ArticleCourse articleCourse = ArticleCourseConverter.INSTANCE.toArticleCourse(saveReq);
        courseRepository.save(articleCourse);
        return articleCourse.getId();
    }

    @Override
    public Integer editCourse(CourseSaveReq editReq) {
        ArticleCourse oldCourse = courseRepository.getById(editReq.getId());
        if (oldCourse == null) {
            throw new FcBusinessException(FcErrorCodeEnum.BAD_REQUEST, "文章教程不存在");
        }
        // TODO : by mfc on 2024/8/25 暂时不做用户限制
        ArticleCourse articleCourse = ArticleCourseConverter.INSTANCE.toArticleCourse(editReq);
        courseRepository.updateById(articleCourse);
        return articleCourse.getId();
    }

    @Override
    public CourseRes getById(Integer id) {
        ArticleCourse articleCourse = courseRepository.getById(id);
        CourseRes courseRes = ArticleCourseConverter.INSTANCE.toCourseRes(articleCourse);
        if (courseRes == null) {
            return null;
        }
        List<CourseDetailRes> details = courseDetailRepository.lambdaQuery()
                .eq(CourseDetail::getCourseId, courseRes.getId())
                .list()
                .stream()
                .map(ArticleCourseConverter.INSTANCE::toCourseDetailRes)
                .collect(Collectors.toList());
        courseRes.setDetails(details);
        return courseRes;
    }

    @Override
    public FcPageRes<CourseRes> pageCourse(CoursePageReq pageReq) {
        Page<ArticleCourse> page = courseRepository.lambdaQuery()
                .eq(Objects.nonNull(pageReq.getStatus()), ArticleCourse::getStatus, pageReq.getStatus())
                .like(StrUtil.isNotBlank(pageReq.getTitle()), ArticleCourse::getTitle, pageReq.getTitle())
                .orderByAsc(ArticleCourse::getOrderNum)
                .orderByDesc(ArticleCourse::getId)
                .page(new Page<>(pageReq.getPage(), pageReq.getPageSize()));

        return new FcPageRes<CourseRes>(pageReq)
                .total(page.getTotal())
                .records(ArticleCourseConverter.INSTANCE.toCourseResList(page.getRecords()));
    }

    @Override
    public Boolean delCourse(Integer id) {
        return courseRepository.removeById(id);
    }

    @Override
    public Boolean uptOrderNum(Integer id, Integer orderNum) {
        return courseRepository.lambdaUpdate()
                .set(ArticleCourse::getOrderNum, orderNum)
                .eq(ArticleCourse::getId, id)
                .update();
    }

    @Override
    public Integer addCourseDetail(CourseDetailSaveReq saveReq) {
        CourseDetail courseDetail = ArticleCourseConverter.INSTANCE.toCourseDetail(saveReq);
        courseDetailRepository.save(courseDetail);
        return courseDetail.getId();
    }

    @Override
    public Integer editCourseDetail(CourseDetailSaveReq editReq) {
        CourseDetail courseDetail = ArticleCourseConverter.INSTANCE.toCourseDetail(editReq);
        courseDetailRepository.updateById(courseDetail);
        return courseDetail.getId();
    }

    @Override
    public Boolean delCourseDetail(Integer detailId) {
        return courseDetailRepository.removeById(detailId);
    }


    @Component
    static class ArticleCourseRepository extends ServiceImpl<ArticleCourseMapper, ArticleCourse> {

    }

    @Component
    static class CourseDetailRepository extends ServiceImpl<CourseDetailMapper, CourseDetail> {

    }
}
