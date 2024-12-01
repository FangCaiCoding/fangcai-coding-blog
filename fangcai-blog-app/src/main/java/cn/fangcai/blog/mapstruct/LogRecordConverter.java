package cn.fangcai.blog.mapstruct;

import cn.fangcai.blog.model.entity.LogRecord;
import cn.fangcai.blog.model.entity.User;
import cn.fangcai.blog.model.res.UserRes;
import cn.fangcai.starter.log.dto.LogRecordDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:50
 * @description
 */
@Mapper
public interface LogRecordConverter {
    LogRecordConverter INSTANCE = Mappers.getMapper(LogRecordConverter.class);

    List<LogRecord> dtoListToEntityList(List<LogRecordDto> dtoList);


}
