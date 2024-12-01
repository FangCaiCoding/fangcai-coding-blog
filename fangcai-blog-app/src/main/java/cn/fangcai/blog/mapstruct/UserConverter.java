package cn.fangcai.blog.mapstruct;

import cn.fangcai.blog.core.model.entity.User;
import cn.fangcai.blog.core.model.res.UserRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:50
 * @description
 */
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserRes userToRes(User user);

    List<UserRes> userListToResList(List<User> userList);


}
