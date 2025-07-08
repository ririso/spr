package com.spr.infrastructure;

import com.spr.application.dto.UserDto;
import com.spr.infrastructure.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsersQueryDataSourceImpl implements UsersQueryDataSource {
    private final UsersMapper usersMapper;

    @Override
    public UserDto getUser(final Integer userId) {
        return usersMapper.getUser(userId);
    }

}

