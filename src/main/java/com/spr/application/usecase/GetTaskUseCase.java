package com.spr.application.usecase;

import com.spr.application.dto.TaskDto;
import com.spr.infrastructure.TasksQueryDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GetTaskUseCase {
    private final TasksQueryDataSource tasksQueryDataSource;

    public TaskDto execute(Integer taskId) {
        System.out.println("GetTaskUseCase");
        return tasksQueryDataSource.getTask(taskId);
    }
}
