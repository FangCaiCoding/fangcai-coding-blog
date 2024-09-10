package cn.fangcai.blog.generator;

import cn.fangcai.blog.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

/**
 * @author MouFangCai
 * @date 2023/3/20 23:27
 * @description
 */
public class MybatisPlusGer {

    private static final String dbUrl = "jdbc:mysql://xxxxx:xxx/xxx";

    private static final String userName = "DbUserName";
    private static final String password = "DbUserPwd";

    public static void main(String[] args) {
        FastAutoGenerator.create(dbUrl + "?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&serverTimezone=GMT%2B8",
                        userName, password)
                .globalConfig(builder -> builder
                        .author("MouFangCai")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
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
                .strategyConfig(builder -> builder.addInclude("article", "article_detail"))
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
