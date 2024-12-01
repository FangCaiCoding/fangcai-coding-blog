package cn.fangcai.blog.listener;

import cn.fangcai.blog.core.model.res.UserRes;
import cn.fangcai.blog.core.service.IUserService;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author MouFangCai
 * @date 2024/11/20 23:00
 * @description
 */
@Service
@Slf4j
public class AppStartListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private IUserService userService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String defaultUser = "ToHeroAdmin";
        UserRes adminUser = userService.getByLoginName(defaultUser);
        if (adminUser == null) {
            String passWord = RandomUtil.randomString(16);
            userService.register(defaultUser, defaultUser, passWord);
            log.error("\n----------------------------------------------------------\n\t" +
                            "初始化账号成功! \n\t" +
                            "账号: \t{}\n\t" +
                            "密码: \t{}\n\t" +
                            "如需要授权超管权限，请手动执行SQL语句：: \n\t" +
                            "INSERT INTO user_role (user_id, role_id,operator) VALUES(" +
                            "(SELECT id FROM user WHERE login_name = 'ToHeroAdmin')," +
                            "(SELECT id FROM role WHERE name = '超级管理员'),1);  \n" +
                            "----------------------------------------------------------",
                    defaultUser,
                    passWord);
        } else {
            log.error("\n----------------------------------------------------------\n\t" +
                            "初始账号已存在，无需初始化！\n\t" +
                            "账号: \t{}\n\t" +
                            "若需要重新初始化请先从数据库中删除该账号后再重启该应用。\n" +
                            "----------------------------------------------------------",
                    defaultUser);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
