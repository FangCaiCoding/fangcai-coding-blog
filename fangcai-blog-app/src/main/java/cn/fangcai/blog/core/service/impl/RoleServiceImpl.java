package cn.fangcai.blog.core.service.impl;

import cn.fangcai.blog.core.mapper.RoleMapper;
import cn.fangcai.blog.core.mapper.RoleMenuMapper;
import cn.fangcai.blog.core.model.entity.Role;
import cn.fangcai.blog.core.model.entity.RoleMenu;
import cn.fangcai.blog.core.service.IMenuService;
import cn.fangcai.blog.core.service.IRoleService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息（带角色权限关系） 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-25
 */
@Service
public class RoleServiceImpl implements IRoleService {


    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;
    @Autowired
    private IMenuService menuService;


    @Override
    public Boolean delById(Integer id) {
        return roleRepository.removeById(id);
    }

    @Override
    public Set<String> listAuthCode(List<Integer> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return new HashSet<>();
        }
        List<Integer> enableRoleIds = roleRepository.lambdaQuery()
                .select(Role::getId)
                .eq(Role::getEnabled, true)
                .in(Role::getId, roleIds)
                .list()
                .stream()
                .map(Role::getId)
                .toList();
        if (CollUtil.isEmpty(enableRoleIds)) {
            return new HashSet<>();
        }
        List<Integer> meunIdList = roleMenuRepository.lambdaQuery()
                .select(RoleMenu::getMenuId)
                .in(RoleMenu::getRoleId, enableRoleIds)
                .list()
                .stream()
                .map(RoleMenu::getMenuId)
                .toList();
        return menuService.listAuthCodeByIds(meunIdList);
    }


    @Repository
    static class RoleRepository extends ServiceImpl<RoleMapper, Role> {

    }

    @Repository
    static class RoleMenuRepository extends ServiceImpl<RoleMenuMapper, RoleMenu> {

    }
}
