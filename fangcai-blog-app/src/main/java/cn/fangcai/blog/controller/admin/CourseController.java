package cn.fangcai.blog.controller.admin;


import cn.fangcai.blog.core.model.req.CourseDetailSaveReq;
import cn.fangcai.blog.core.model.req.CoursePageReq;
import cn.fangcai.blog.core.model.req.CourseSaveReq;
import cn.fangcai.blog.core.model.res.CourseRes;
import cn.fangcai.blog.core.service.ICourseService;
import cn.fangcai.starter.auth.FcAuthContext;
import cn.fangcai.starter.auth.ano.FcCheckAuth;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.common.model.valider.EditGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文章教程 前端控制器
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-16
 */
@RestController
@Tag(name = "文章教程管理")
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @Operation(summary = "新增文章教程")
    @PostMapping
    @FcCheckAuth(values = {"course:add"})
    public FcResult<Integer> addCourse(@RequestBody @Validated CourseSaveReq saveReq) {
        saveReq.setUserId(FcAuthContext.getUserIdAsInt());
        return FcResult.SUCCESS(courseService.addCourse(saveReq));
    }

    @Operation(summary = "编辑文章教程")
    @PutMapping
    @FcCheckAuth(values = {"course:edit"})
    public FcResult<Integer> editCourse(@RequestBody @Validated(EditGroup.class) CourseSaveReq editReq) {
        return FcResult.SUCCESS(courseService.editCourse(editReq));
    }

    @Operation(summary = "获取文章教程")
    @GetMapping("/{id}")
    @FcCheckAuth(values = {"course:get"})
    public FcResult<CourseRes> getById(@PathVariable Integer id) {
        return FcResult.SUCCESS(courseService.getById(id));
    }


    @Operation(summary = "分页查询文章教程")
    @PostMapping("/page")
    @FcCheckAuth(values = {"course:page"})
    public FcResult<FcPageRes<CourseRes>> pageCourse(@RequestBody @Validated CoursePageReq pageReq) {
        return FcResult.SUCCESS(courseService.pageCourse(pageReq));
    }

    @Operation(summary = "删除文章教程")
    @DeleteMapping("/{id}")
    @FcCheckAuth(values = {"course:del"})
    public FcResult<Boolean> delCourse(@PathVariable Integer id) {
        return FcResult.SUCCESS(courseService.delCourse(id));
    }


    @Operation(summary = "修改文章教程顺序")
    @PutMapping("/orderNum/{id}")
    @FcCheckAuth(values = {"course:orderNum"})
    public FcResult<Boolean> uptOrderNum(@PathVariable Integer id, @RequestParam Integer orderNum) {
        return FcResult.SUCCESS(courseService.uptOrderNum(id, orderNum));
    }


    /**
     * 以下为 文章教程详情 管理接口
     */


    @Operation(summary = "新增文章教程详情")
    @PostMapping("/detail")
    @FcCheckAuth(values = {"course:addDetail"})
    public FcResult<Boolean> addCourseDetail(@RequestBody @Validated List<CourseDetailSaveReq> saveReqList) {
        return FcResult.SUCCESS(courseService.addCourseDetail(saveReqList));
    }

    @Operation(summary = "编辑文章教程详情")
    @PutMapping("/detail")
    @FcCheckAuth(values = {"course:editDetail"})
    public FcResult<Integer> editCourseDetail(@RequestBody @Validated CourseDetailSaveReq editReq) {
        return FcResult.SUCCESS(courseService.editCourseDetail(editReq));
    }

    @Operation(summary = "删除教程中的文章")
    @DeleteMapping("/detail")
    @FcCheckAuth(values = {"course:delDetail"})
    public FcResult<Boolean> delCourseDetail(@RequestBody List<Integer> detailIds) {
        return FcResult.SUCCESS(courseService.delCourseDetail(detailIds));
    }


}
