package cn.fangcai.blog.core.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:40
 * @description
 */
@Schema(description = "用户信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {

    @Schema(description = "用户ID")
    private Integer id;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "头像文字（显示优先级低于头像）")
    private String avatarStr;

    @Schema(description = "权限列表")
    private Set<String> authCodeSet;

    @Schema(description = "是否启用")
    private Boolean enabled;

    @Schema(description = "是否VIP")
    private Boolean isVip;

    @Schema(description = "VIP 到期时间")
    private  LocalDateTime vipEndTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    public void initVip(){
        LocalDateTime vipEndTime = this.getVipEndTime();
        Boolean isVip = vipEndTime != null && vipEndTime.isAfter(LocalDateTime.now());
        this.setIsVip(isVip);
    }
}
