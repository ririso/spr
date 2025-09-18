package com.spr.presentation;


import com.spr.application.usecase.GetTaskUseCase;
import com.spr.application.usecase.GetTasksUseCase;
import com.spr.generated.model.GetCommonTasksResponse;
import com.spr.generated.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class CommonApiController implements CommonApi {
    private final GetTaskUseCase getTaskUseCase;
    private final GetTasksUseCase getTasksUseCase;

    @Override
    public ResponseEntity<Task> getTask(Integer taskId) {
        final var task = getTaskUseCase.execute(taskId);
        return ResponseEntity.ok(new Task(task.taskId(),task.userId(), task.taskName()));
    }

    @Override
    public ResponseEntity<GetCommonTasksResponse> getTasks(Integer userId) {
        final var taskDtoList = getTasksUseCase.execute(userId);
        final var taskList = taskDtoList.stream()
                .map(taskDto -> new Task(taskDto.taskId(), taskDto.userId(), taskDto.taskName()))
                .toList();
        return ResponseEntity.ok(new GetCommonTasksResponse().tasks(taskList));
    }
}