package cn.fangcai.blog.mapper;


import cn.fangcai.blog.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author MouFangCai
 * @since 2023-03-21
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
