package cn.fangcai.blog.core.service.impl;

import cn.fangcai.blog.core.model.entity.Menu;
import cn.fangcai.blog.core.mapper.MenuMapper;
import cn.fangcai.blog.core.service.IMenuService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单（权限）列表 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-25
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuRepository menuRepository;



    @Override
    public Set<String> listAuthCodeByIds(List<Integer> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new HashSet<>();
        }
        return menuRepository.lambdaQuery()
                .select(Menu::getAuthCodeList)
                .in(Menu::getId, ids)
                .eq(Menu::getEnabled, true)
                .list()
                .stream()
                .map(Menu::getAuthCodeList)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }
    @Repository
    static  class  MenuRepository  extends ServiceImpl<MenuMapper, Menu>{
    }

}
