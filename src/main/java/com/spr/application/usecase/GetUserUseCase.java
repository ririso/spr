package com.spr.application.usecase;

import com.spr.application.dto.UserDto;
import com.spr.infrastructure.UsersQueryDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GetUserUseCase {
    private final UsersQueryDataSource usersQueryDataSource;

    public UserDto execute(Integer userId) {
        return usersQueryDataSource.getUser(userId);
    }
}
