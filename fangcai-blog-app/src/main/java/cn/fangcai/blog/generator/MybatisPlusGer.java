package cn.fangcai.blog.generator;

import cn.fangcai.blog.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author MouFangCai
 * @date 2023/3/20 23:27
 * @description
 */
@Slf4j
public class MybatisPlusGer {

    private static final List<String> tables = Arrays.asList("config_wechat");

    public static void main(String[] args) {
        String dbUrl = args[0];
        String userName = args[1];
        String password = args[2];

        log.error("dbUrl = {}",dbUrl);
        log.error("userName = {}",userName);
        log.error("password = {}",password);
        FastAutoGenerator.create(dbUrl + "?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&serverTimezone=GMT%2B8",
                        userName, password)
                .globalConfig(builder -> builder
                        .author("MouFangCai")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/fangcai-blog-app/src/main/java")
                        .commentDate("yyyy-MM-dd")
                        .enableSpringdoc()
                )
                .packageConfig(builder -> builder
                        .parent("cn.fangcai.blog")
                        .entity("model.entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                        .superClass(BaseEntity.class)
                        .enableTableFieldAnnotation()
                        .enableActiveRecord()
                )
                .strategyConfig(builder -> builder.addInclude(tables))
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
