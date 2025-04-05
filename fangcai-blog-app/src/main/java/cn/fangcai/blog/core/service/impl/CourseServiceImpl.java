package cn.fangcai.blog.core.service.impl;

import cn.fangcai.blog.consts.StatusEnum;
import cn.fangcai.blog.core.model.entity.Course;
import cn.fangcai.blog.core.model.entity.CourseDetail;
import cn.fangcai.blog.core.model.res.ArticleRes;
import cn.fangcai.blog.core.model.res.CourseDetailRes;
import cn.fangcai.blog.core.model.res.CourseRes;
import cn.fangcai.blog.core.mapper.CourseDetailMapper;
import cn.fangcai.blog.core.mapper.CourseMapper;
import cn.fangcai.blog.mapstruct.CourseConverter;
import cn.fangcai.blog.core.model.req.CourseDetailSaveReq;
import cn.fangcai.blog.core.model.req.CoursePageReq;
import cn.fangcai.blog.core.model.req.CourseSaveReq;
import cn.fangcai.blog.core.service.IArticleService;
import cn.fangcai.blog.core.service.ICourseService;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.enums.FcErrorCodeEnum;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
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
                .orderByAsc(CourseDetail::getOrderNum)
                .list()
                .stream()
                .map(CourseConverter.INSTANCE::toCourseDetailRes)
                .collect(Collectors.toList());
        List<Integer> articleIdList = details.stream().map(CourseDetailRes::getArticleId).toList();
        List<ArticleRes> articleList = articleService.listByIds(articleIdList);
        Map<Integer, ArticleRes> articleMap = articleList.stream()
                .collect(Collectors.toMap(ArticleRes::getId, article -> article));
        details.forEach(detail -> {
            ArticleRes article = articleMap.get(detail.getArticleId());
            if (article != null) {
                detail.setArticleTitle(article.getTitle());
                detail.setLimitType(article.getLimitType());
            }
        });
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
    public Boolean addCourseDetail(List<CourseDetailSaveReq> saveReqList) {
        if (CollUtil.isEmpty(saveReqList)) {
            return false;
        }
        long courseIdKeyCt = saveReqList.stream().map(CourseDetailSaveReq::getCourseId).distinct().count();
        if (courseIdKeyCt > 1) {
            throw new FcBusinessException(FcErrorCodeEnum.BAD_REQUEST, "只能关联一个教程");
        }
        if (saveReqList.stream().map(CourseDetailSaveReq::getArticleId).distinct().count() != saveReqList.size()) {
            throw new FcBusinessException(FcErrorCodeEnum.BAD_REQUEST, "文章不能重复关联");
        }
        Set<Integer> existArticleIdSet = this.getById(saveReqList.getFirst().getCourseId()).getDetails()
                .stream().map(CourseDetailRes::getArticleId).collect(Collectors.toSet());
        saveReqList = saveReqList.stream().filter(req -> !existArticleIdSet.contains(req.getArticleId()))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(saveReqList)) {
            return false;
        }
        List<CourseDetail> courseDetailList = CourseConverter.INSTANCE.toCourseDetailList(saveReqList);
        return courseDetailRepository.saveBatch(courseDetailList);
    }

    @Override
    public Integer editCourseDetail(CourseDetailSaveReq editReq) {
        CourseDetail courseDetail = CourseConverter.INSTANCE.toCourseDetail(editReq);
        courseDetailRepository.updateById(courseDetail);
        return courseDetail.getId();
    }

    @Override
    public Boolean delCourseDetail(List<Integer> detailIds) {
        return courseDetailRepository.removeByIds(detailIds);
    }


    @Override
    public Map<Integer, Integer> getMapByArticleIds(List<Integer> articleIdList) {
        if (CollUtil.isEmpty(articleIdList)) {
            return new HashMap<>();
        }
        List<CourseDetail> courseDetailList = courseDetailRepository.lambdaQuery()
                .in(CourseDetail::getArticleId, articleIdList)
                .list();
        if (CollUtil.isEmpty(courseDetailList)) {
            return new HashMap<>();
        }
        List<Integer> courseIdList = courseDetailList.stream().map(CourseDetail::getCourseId).toList();
        // 查询正常已发布的课程
        Set<Integer> publishedCourseIdSet = courseRepository.lambdaQuery()
                .select(Course::getId)
                .in(Course::getId, courseIdList)
                .eq(Course::getStatus, StatusEnum.PUBLISHED.getCode())
                .list()
                .stream()
                .map(Course::getId)
                .collect(Collectors.toSet());

        return courseDetailList
                .stream()
                .filter(detail -> publishedCourseIdSet.contains(detail.getCourseId()))
                .collect(Collectors.toMap(CourseDetail::getArticleId,
                        CourseDetail::getCourseId, (k1, k2) -> k2));
    }

    @Override
    public List<Integer> listArticleIdById(Integer courseId) {
        return courseDetailRepository.lambdaQuery()
                .eq(CourseDetail::getCourseId, courseId)
                .list()
                .stream()
                .map(CourseDetail::getArticleId)
                .toList();
    }

    @Component
    static class CourseRepository extends ServiceImpl<CourseMapper, Course> {

    }

    @Component
    static class CourseDetailRepository extends ServiceImpl<CourseDetailMapper, CourseDetail> {

    }
}
