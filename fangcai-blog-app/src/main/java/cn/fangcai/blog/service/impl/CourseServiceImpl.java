package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.mapper.CourseDetailMapper;
import cn.fangcai.blog.mapper.CourseMapper;
import cn.fangcai.blog.mapstruct.CourseConverter;
import cn.fangcai.blog.model.entity.Course;
import cn.fangcai.blog.model.entity.CourseDetail;
import cn.fangcai.blog.model.req.CourseDetailSaveReq;
import cn.fangcai.blog.model.req.CoursePageReq;
import cn.fangcai.blog.model.req.CourseSaveReq;
import cn.fangcai.blog.model.res.ArticleRes;
import cn.fangcai.blog.model.res.CourseDetailRes;
import cn.fangcai.blog.model.res.CourseRes;
import cn.fangcai.blog.service.IArticleService;
import cn.fangcai.blog.service.ICourseService;
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
import java.util.Map;
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
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseDetailRepository courseDetailRepository;
    @Autowired
    private IArticleService articleService;


    @Override
    public Integer addCourse(CourseSaveReq saveReq) {
        Course Course = CourseConverter.INSTANCE.toCourse(saveReq);
        courseRepository.save(Course);
        return Course.getId();
    }

    @Override
    public Integer editCourse(CourseSaveReq editReq) {
        Course oldCourse = courseRepository.getById(editReq.getId());
        if (oldCourse == null) {
            throw new FcBusinessException(FcErrorCodeEnum.BAD_REQUEST, "文章教程不存在");
        }
        // TODO : by mfc on 2024/8/25 暂时不做用户限制
        Course Course = CourseConverter.INSTANCE.toCourse(editReq);
        courseRepository.updateById(Course);
        return Course.getId();
    }

    @Override
    public CourseRes getById(Integer id) {
        Course Course = courseRepository.getById(id);
        CourseRes courseRes = CourseConverter.INSTANCE.toCourseRes(Course);
        if (courseRes == null) {
            return null;
        }
        List<CourseDetailRes> details = courseDetailRepository.lambdaQuery()
                .eq(CourseDetail::getCourseId, courseRes.getId())
                .list()
                .stream()
                .map(CourseConverter.INSTANCE::toCourseDetailRes)
                .collect(Collectors.toList());
        List<Integer> articleIdList = details.stream().map(CourseDetailRes::getArticleId).toList();
        Map<Integer, String> articleMap = articleService.listByIds(articleIdList)
                .stream()
                .collect(Collectors.toMap(ArticleRes::getId, ArticleRes::getTitle));
        details.forEach(detail -> detail.setArticleTitle(articleMap.get(detail.getArticleId())));
        courseRes.setDetails(details);
        return courseRes;
    }

    @Override
    public Boolean incrReadCt(Integer id) {
        return courseRepository.lambdaUpdate()
                .setSql("read_ct = read_ct + 1")
                .eq(Course::getId, id)
                .update();
    }

    @Override
    public FcPageRes<CourseRes> pageCourse(CoursePageReq pageReq) {
        Page<Course> page = courseRepository.lambdaQuery()
                .eq(Objects.nonNull(pageReq.getStatus()), Course::getStatus, pageReq.getStatus())
                .like(StrUtil.isNotBlank(pageReq.getTitle()), Course::getTitle, pageReq.getTitle())
                .orderByAsc(Course::getOrderNum)
                .orderByDesc(Course::getId)
                .page(new Page<>(pageReq.getPage(), pageReq.getPageSize()));

        return new FcPageRes<CourseRes>(pageReq)
                .total(page.getTotal())
                .records(CourseConverter.INSTANCE.toCourseResList(page.getRecords()));
    }

    @Override
    public Boolean delCourse(Integer id) {
        return courseRepository.removeById(id);
    }

    @Override
    public Boolean uptOrderNum(Integer id, Integer orderNum) {
        return courseRepository.lambdaUpdate()
                .set(Course::getOrderNum, orderNum)
                .eq(Course::getId, id)
                .update();
    }

    @Override
    public Integer addCourseDetail(CourseDetailSaveReq saveReq) {
        CourseDetail courseDetail = CourseConverter.INSTANCE.toCourseDetail(saveReq);
        courseDetailRepository.save(courseDetail);
        return courseDetail.getId();
    }

    @Override
    public Integer editCourseDetail(CourseDetailSaveReq editReq) {
        CourseDetail courseDetail = CourseConverter.INSTANCE.toCourseDetail(editReq);
        courseDetailRepository.updateById(courseDetail);
        return courseDetail.getId();
    }

    @Override
    public Boolean delCourseDetail(Integer detailId) {
        return courseDetailRepository.removeById(detailId);
    }


    @Component
    static class CourseRepository extends ServiceImpl<CourseMapper, Course> {

    }

    @Component
    static class CourseDetailRepository extends ServiceImpl<CourseDetailMapper, CourseDetail> {

    }
}
