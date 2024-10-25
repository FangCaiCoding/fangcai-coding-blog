package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.model.entity.Role;
import cn.fangcai.blog.mapper.RoleMapper;
import cn.fangcai.blog.service.IMenuService;
import cn.fangcai.blog.service.IRoleService;
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
    private IMenuService menuService;

    @Override
    public Set<String> listApiCodes(List<Integer> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return new HashSet<>();
        }
        List<Integer> meunIdList = roleRepository.lambdaQuery()
                .select(Role::getMenuIdList)
                .eq(Role::getEnabled, true)
                .in(Role::getId, roleIds)
                .list()
                .stream()
                .map(Role::getMenuIdList)
                .flatMap(List::stream)
                .distinct()
                .toList();
       return menuService.listApiCodeByIds(meunIdList);
    }



    @Repository
    static class RoleRepository extends ServiceImpl<RoleMapper, Role> {

    }
}
