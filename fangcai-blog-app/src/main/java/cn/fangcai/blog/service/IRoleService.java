package cn.fangcai.blog.service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息（带角色权限关系） 服务类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-25
 */
public interface IRoleService {

    Set<String> listApiCodes(List<Integer> roleIds);
}
