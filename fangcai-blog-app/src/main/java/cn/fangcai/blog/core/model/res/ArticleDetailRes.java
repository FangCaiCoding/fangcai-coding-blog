package cn.fangcai.blog.core.model.res;

import cn.fangcai.blog.consts.ReadLimitTypeEnum;
import cn.fangcai.blog.core.model.dto.UserAuthDto;
import cn.fangcai.blog.uitls.FcStrUtil;
import cn.fangcai.starter.auth.FcAuthContext;
import cn.fangcai.starter.auth.FcAuthUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:23
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文章详情")
@Data
public class ArticleDetailRes extends ArticleRes {

    @Schema(description = " 模板id")
    private Integer templateId;

    // @Schema(description = "文章内容-html格式")
    // private String content;

    @Schema(description = "文章内容 markdown 格式")
    private String contentMd;


    @Schema(description = "阅读限制类型：0-不限制 1-需登录 2-需VIP")
    private Byte limitType;


    @Schema(description = "内容是否结束")
    private Boolean contendIsEnd = true;


    /**
     * 处理阅读限制
     */
    public void handleReadLimit() {
        if (ReadLimitTypeEnum.NO_LIMIT.getCode().equals(limitType)) {
            return;
        }
        Byte readLimitRatio = this.getReadLimitRatio();
        if (readLimitRatio == null || readLimitRatio >= 100) {
            return;
        }

        // 需登录
        if (ReadLimitTypeEnum.LOGIN_REQUIRED.getCode().equals(limitType) && FcAuthUtil.isLogin()) {
            return;
        }
        // 需VIP
        if (ReadLimitTypeEnum.VIP_REQUIRED.getCode().equals(limitType) && FcAuthUtil.isLogin()
                && ((UserAuthDto) FcAuthContext.getAuthInfo()).getIsVip()) {
            return;
        }
        this.setContentMd(FcStrUtil.subStrByLinesLimit(this.getContentMd(), readLimitRatio));
        this.setContendIsEnd(false);
    }
}
