package com.spr.application.usecase;

import com.spr.application.dto.TaskDto;
import com.spr.infrastructure.TasksQueryDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GetTasksUseCase {
    private final TasksQueryDataSource tasksQueryDataSource;

    public List<TaskDto> execute(Integer userId) {
        System.out.println("GetTasksUseCase");
        return tasksQueryDataSource.getTasks(userId);
    }
}
