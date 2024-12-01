package cn.fangcai.blog.controller.rbac;


import cn.fangcai.blog.core.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MouFangCai
 * @date 2024/12/1 23:05
 * @description
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户相关")
public class UserController {

    @Autowired
    private IUserService userService;




}
