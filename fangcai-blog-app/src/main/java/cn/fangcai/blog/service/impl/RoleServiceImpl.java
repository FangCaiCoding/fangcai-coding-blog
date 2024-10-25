package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.model.entity.Role;
import cn.fangcai.blog.mapper.RoleMapper;
import cn.fangcai.blog.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息（带角色权限关系） 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
