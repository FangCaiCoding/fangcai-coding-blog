package cn.fangcai.blog.core.service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单（权限）列表 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-25
 */
public interface IMenuService {

    Set<String> listAuthCodeByIds(List<Integer> ids);
}
