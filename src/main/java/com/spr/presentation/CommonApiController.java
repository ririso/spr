package com.spr.presentation;


import com.spr.application.usecase.GetTaskUseCase;
import com.spr.application.usecase.GetTasksUseCase;
import com.spr.application.usecase.GetGoodUseCase;
import com.spr.application.usecase.GetGoodsUseCase;
import com.spr.generated.model.GetCommonGoodsResponse;
import com.spr.generated.model.GetCommonTasksResponse;
import com.spr.generated.model.Task;
import com.spr.generated.model.Good;
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
        final var task = getTaskUseCase.execute(taskId);
        return ResponseEntity.ok(new Task(task.taskId(),task.userId(), task.taskName()));
    }

    @Override
    public ResponseEntity<GetCommonTasksResponse> getTasks(Integer userId) {
        final var tasksList = getTasksUseCase.execute(userId);

//        Mapperで取得したTaskをOpenApiで定義したResponseのTask型に変換する
        final var taskResponseList = tasksList.stream()
                .map(task -> new Task(task.taskId(),task.userId(), task.taskName())) // task.id(), task.name() でデータを取り出し
                .toList();

        return ResponseEntity.ok(new GetCommonTasksResponse(taskResponseList));
    }
}