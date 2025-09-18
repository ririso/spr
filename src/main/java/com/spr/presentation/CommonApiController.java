package com.spr.presentation;


import com.spr.application.usecase.GetTaskUseCase;
import com.spr.generated.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class CommonApiController implements CommonApi {
    private final GetTaskUseCase getTaskUseCase;

    @Override
    public ResponseEntity<Task> getTask(Integer taskId) {
        final var task = getTaskUseCase.execute(taskId);
        return ResponseEntity.ok(new Task(task.taskId(),task.userId(), task.taskName()));
    }
}