package com.spr.presentation;


import com.spr.application.usecase.GetTaskUseCase;
import com.spr.application.usecase.GetTasksUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class CommonApiController implements CommonApi {
    private final GetTasksUseCase getTasksUseCase;
    private final GetTaskUseCase getTaskUseCase;
    private final GetUserUseCase getUserUseCase;

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

    @Override
    public ResponseEntity<User> getUser(Integer userId) {
        final var user = getUserUseCase.execute(userId);
        return ResponseEntity.ok(new User(user.userId(), user.userName(), user.age(), user.team()));
    }

    public ResponseEntity<Good> getGood(Integer goodId) {
        final var good = getGoodUseCase.execute(goodId);
        return ResponseEntity.ok(new Good(good.goodId(), good.userId(), good.goodsName(), good.color(), good.size(), good.quantity(), good.isDeleted()));
    }
    @Override
    public ResponseEntity<GetCommonGoodsResponse> getGoods(Integer userId) {

        final var goodsList = getGoodsUseCase.execute(userId);

        final var goodResponseList = goodsList.stream()
                .map(x -> new Good(x.goodId(), x.userId(),x.goodsName(),x.size(),x.color(),x.quantity(),x.isDeleted()))
                .toList();
        return ResponseEntity.ok(new GetCommonGoodsResponse(goodResponseList));
    }
}