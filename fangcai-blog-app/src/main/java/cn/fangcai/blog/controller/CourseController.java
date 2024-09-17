package cn.fangcai.blog.controller;


import cn.fangcai.blog.model.req.CourseDetailSaveReq;
import cn.fangcai.blog.model.req.CoursePageReq;
import cn.fangcai.blog.model.req.CourseSaveReq;
import cn.fangcai.blog.model.res.CourseRes;
import cn.fangcai.blog.service.ICourseService;
import cn.fangcai.common.auth.FcAuthContext;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.common.model.valider.EditGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章教程 前端控制器
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-16
 */
@Controller
@Tag(name = "文章教程管理")
@RequestMapping("/article/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @Operation(summary = "新增文章教程")
    @PostMapping
    public FcResult<Integer> addCourse(@RequestBody @Validated CourseSaveReq saveReq) {
        saveReq.setUserId(FcAuthContext.getUserIdAsInt());
        return FcResult.SUCCESS(courseService.addCourse(saveReq));
    }

    @Operation(summary = "编辑文章教程")
    @PutMapping
    public FcResult<Integer> editCourse(@RequestBody @Validated(EditGroup.class) CourseSaveReq editReq) {
        return FcResult.SUCCESS(courseService.editCourse(editReq));
    }

    @Operation(summary = "获取文章教程")
    @GetMapping("/{id}")
    public FcResult<CourseRes> getById(@PathVariable Integer id) {
        return FcResult.SUCCESS(courseService.getById(id));
    }


    @Operation(summary = "分页查询文章教程")
    @PostMapping("/page")
    public FcResult<FcPageRes<CourseRes>> pageCourse(@RequestBody @Validated CoursePageReq pageReq) {
        return FcResult.SUCCESS(courseService.pageCourse(pageReq));
    }

    @Operation(summary = "删除文章教程")
    @DeleteMapping("/{id}")
    public FcResult<Boolean> delCourse(@PathVariable Integer id) {
        return FcResult.SUCCESS(courseService.delCourse(id));
    }


    @Operation(summary = "修改文章教程顺序")
    @PutMapping("/orderNum/{id}")
    public FcResult<Boolean> uptOrderNum(@PathVariable Integer id, @RequestParam Integer orderNum) {
        return FcResult.SUCCESS(courseService.uptOrderNum(id, orderNum));
    }


    /**
     * 以下为 文章教程详情 管理接口
     */


    @Operation(summary = "新增文章教程详情")
    @PostMapping("/detail")
    public FcResult<Integer> addCourseDetail(@RequestBody @Validated CourseDetailSaveReq saveReq) {
        return FcResult.SUCCESS(courseService.addCourseDetail(saveReq));
    }

    @Operation(summary = "编辑文章教程详情")
    @PutMapping("/detail")
    public FcResult<Integer> editCourseDetail(@RequestBody @Validated CourseDetailSaveReq editReq) {
        return FcResult.SUCCESS(courseService.editCourseDetail(editReq));
    }

    @Operation(summary = "删除文章教程")
    @DeleteMapping("/detail/{detailId}")
    public FcResult<Boolean> delCourseDetail(@PathVariable Integer detailId) {
        return FcResult.SUCCESS(courseService.delCourseDetail(detailId));
    }


}
