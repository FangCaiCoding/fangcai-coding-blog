package cn.fangcai.blog.consts;

import cn.fangcai.blog.core.model.entity.Article;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.Getter;

/**
 * @author MouFangCai
 * @date 2024/10/27 17:30
 * @description 排序字段枚举
 */
@Getter
public enum ArticleOrderByFiledEnum {

    ORDER_BY_DEFAULT((byte) 0, Article::getOrderNum),

    READ_COUNT((byte) 1, Article::getReadCt),

    CREATE_TIME((byte) 2, Article::getCreateTime),

    ;

    private final Byte code;
    private final SFunction<Article, ?> filedName;

    ArticleOrderByFiledEnum(Byte code,SFunction<Article, ?> filedName) {
        this.code = code;
        this.filedName = filedName;
    }


    public static SFunction<Article, ?> getOrderByFiled(Byte code) {
        for (ArticleOrderByFiledEnum e : ArticleOrderByFiledEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getFiledName();
            }
        }
        return ORDER_BY_DEFAULT.getFiledName();
    }
}