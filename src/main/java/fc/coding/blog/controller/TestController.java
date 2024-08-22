package fc.coding.blog.controller;

import cn.fangcai.common.auth.dto.UserTokenDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MouFangCai
 * @date 2024/8/20 23:13
 * @description
 */
@RestController
@RequestMapping("test")
public class TestController {

    @PostMapping
    public UserTokenDto test(@RequestBody UserTokenDto userToken) {
        return new UserTokenDto(1, "123");

    }
}
