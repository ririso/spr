package com.spr.infrastructure;

import com.spr.application.dto.TaskDto;

import java.util.List;


public interface TasksQueryDataSource {
    List<TaskDto> getTasks(Integer userId);

    TaskDto getTask(Integer taskId);
}
