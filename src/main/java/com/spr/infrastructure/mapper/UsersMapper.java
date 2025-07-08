package com.spr.infrastructure.mapper;

import com.spr.application.dto.TaskDto;
import com.spr.application.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersMapper {
    UserDto getUser(Integer userId);
}