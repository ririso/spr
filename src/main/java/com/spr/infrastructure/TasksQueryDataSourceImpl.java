package com.spr.infrastructure;

import com.spr.application.dto.TaskDto;
import com.spr.infrastructure.mapper.TasksMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TasksQueryDataSourceImpl implements TasksQueryDataSource {
    private final TasksMapper tasksMapper;

    @Override
    public List<TaskDto> getTasks(final Integer userId) {
        System.out.println("getTasks");
        final var result = tasksMapper.getTasks(userId);
        System.out.println("result" + result);

        return result;
    }

    @Override
    public TaskDto getTask(final Integer taskId) {
        System.out.println("TasksQueryDataSourceImpl");
        System.out.println("tasksMapper is null? " + (tasksMapper == null));
        System.out.println("tasksMapper? " + tasksMapper );

        return tasksMapper.getTask(taskId);
    }

}

