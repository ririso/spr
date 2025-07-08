package com.spr.infrastructure;

import com.spr.application.dto.UserDto;

public interface UsersQueryDataSource {
    UserDto getUser(Integer userId);
}



