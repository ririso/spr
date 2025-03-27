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
    private final GetTasksUseCase getTasksUseCase;
    private final GetTaskUseCase getTaskUseCase;


    @Override
    public ResponseEntity<Task> getTask(Integer taskId) {
        System.out.println("************************************");
        System.out.println("Controller");
        System.out.println("id" + taskId);
        final var task = getTaskUseCase.execute(taskId);
        return ResponseEntity.ok(new Task(task.taskId(),task.userId(), task.taskName()));
    }

    @Override
    public ResponseEntity<GetCommonTasksResponse> getTasks(Integer userId) {

        System.out.println("************************************");
        System.out.println("Controller");
        System.out.println("userId" + userId);

        final var tasksList = getTasksUseCase.execute(userId);

//        Mapperで取得したTaskをOpenApiで定義したResponseのTask型に変換する
        final var taskResponseList = tasksList.stream()
                .map(task -> new Task(task.taskId(),task.userId(), task.taskName())) // task.id(), task.name() でデータを取り出し
                .toList();

        return ResponseEntity.ok(new GetCommonTasksResponse(taskResponseList));
    }



}