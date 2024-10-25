package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.model.entity.Menu;
import cn.fangcai.blog.mapper.MenuMapper;
import cn.fangcai.blog.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单（权限）列表 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-25
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
