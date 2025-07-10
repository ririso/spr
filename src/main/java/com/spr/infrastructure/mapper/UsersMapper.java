package com.spr.infrastructure.mapper;

import com.spr.application.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    UserDto getUser(Integer userId);
}