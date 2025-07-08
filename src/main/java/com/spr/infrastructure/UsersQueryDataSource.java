package com.spr.infrastructure;

import com.spr.application.dto.TaskDto;
import com.spr.application.dto.UserDto;

import java.util.List;


public interface UsersQueryDataSource {
    UserDto getUser(Integer userId);
}



