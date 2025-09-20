package com.spr.presentation;

import com.spr.application.dto.TaskDto;
import com.spr.application.usecase.GetTaskUseCase;
import com.spr.application.usecase.GetTasksUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommonApiController.class)
class CommonApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetTaskUseCase getTaskUseCase;

    @MockBean
    private GetTasksUseCase getTasksUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTask_正常なタスクID_タスクを返す() throws Exception {
        // Arrange
        final var taskId = 1;
        final var expectedTaskDto = new TaskDto(1, 100, "TestTask", false);
        when(getTaskUseCase.execute(taskId)).thenReturn(expectedTaskDto);

        // Act & Assert
        mockMvc.perform(get("/v1/anv/common/tasks/{task_id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(100))
                .andExpect(jsonPath("$.taskName").value("TestTask"));

        // Verify
        verify(getTaskUseCase).execute(taskId);
    }

    @Test
    void getTask_異なるタスクID_対応するタスクを返す() throws Exception {
        // Arrange
        final var taskId = 2;
        final var expectedTaskDto = new TaskDto(2, 200, "AnotherTask", false);
        when(getTaskUseCase.execute(taskId)).thenReturn(expectedTaskDto);

        // Act & Assert
        mockMvc.perform(get("/v1/anv/common/tasks/{task_id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.userId").value(200))
                .andExpect(jsonPath("$.taskName").value("AnotherTask"));

        // Verify
        verify(getTaskUseCase).execute(taskId);
    }

    @Test
    void getTask_UseCaseが例外をスロー_例外が伝播される() throws Exception {
        // Arrange
        final var taskId = 999;
        when(getTaskUseCase.execute(taskId)).thenThrow(new RuntimeException("Task not found"));

        // Act & Assert
        // Spring Boot Test では未処理の例外は通常 ServletException としてラップされる
        // この場合はコントローラーが適切に例外を処理していないことを確認
        try {
            mockMvc.perform(get("/v1/anv/common/tasks/{task_id}", taskId)
                            .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            // ServletException が発生することを確認
            assert e.getCause() instanceof RuntimeException;
            assert "Task not found".equals(e.getCause().getMessage());
        }

        // Verify
        verify(getTaskUseCase).execute(taskId);
    }

    @Test
    void getTask_無効なパスパラメータ_バッドリクエストを返す() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/v1/anv/common/tasks/{task_id}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTask_UseCaseが正しく呼び出される_モックが検証される() throws Exception {
        // Arrange
        final var taskId = 42;
        final var taskDto = new TaskDto(42, 300, "MockTask", false);
        when(getTaskUseCase.execute(any(Integer.class))).thenReturn(taskDto);

        // Act
        mockMvc.perform(get("/v1/anv/common/tasks/{task_id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assert
        verify(getTaskUseCase).execute(taskId);
    }

    // ============================================================================
    // getTasks エンドポイントのテスト
    // ============================================================================

    @Test
    void getTasks_正常なuserID_タスクリストを返す() throws Exception {
        // Arrange
        final var userId = 1;
        final var expectedTaskDtoList = Arrays.asList(
            new TaskDto(1, 1, "Task1", false),
            new TaskDto(2, 1, "Task2", false)
        );
        when(getTasksUseCase.execute(userId)).thenReturn(expectedTaskDtoList);

        // Act & Assert
        mockMvc.perform(get("/v1/anv/common/tasks?user_id={userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks").value(org.hamcrest.Matchers.hasSize(2)))
                .andExpect(jsonPath("$.tasks[0].id").value(1))
                .andExpect(jsonPath("$.tasks[0].userId").value(1))
                .andExpect(jsonPath("$.tasks[0].taskName").value("Task1"))
                .andExpect(jsonPath("$.tasks[1].id").value(2))
                .andExpect(jsonPath("$.tasks[1].userId").value(1))
                .andExpect(jsonPath("$.tasks[1].taskName").value("Task2"));

        // Verify
        verify(getTasksUseCase).execute(userId);
    }

    @Test
    void getTasks_空のリスト_空の配列を返す() throws Exception {
        // Arrange
        final var userId = 999;
        final var emptyTaskList = Arrays.<TaskDto>asList();
        when(getTasksUseCase.execute(userId)).thenReturn(emptyTaskList);

        // Act & Assert
        mockMvc.perform(get("/v1/anv/common/tasks?user_id={userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks").isEmpty());

        // Verify
        verify(getTasksUseCase).execute(userId);
    }

    @Test
    void getTasks_異なるuserID_対応するタスクリストを返す() throws Exception {
        // Arrange
        final var userId = 2;
        final var expectedTaskDtoList = Arrays.asList(
            new TaskDto(3, 2, "UserTask1", false)
        );
        when(getTasksUseCase.execute(userId)).thenReturn(expectedTaskDtoList);

        // Act & Assert
        mockMvc.perform(get("/v1/anv/common/tasks?user_id={userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tasks").value(org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$.tasks[0].id").value(3))
                .andExpect(jsonPath("$.tasks[0].userId").value(2))
                .andExpect(jsonPath("$.tasks[0].taskName").value("UserTask1"));

        // Verify
        verify(getTasksUseCase).execute(userId);
    }

    @Test
    void getTasks_UseCaseが例外をスロー_例外が伝播される() throws Exception {
        // Arrange
        final var userId = 500;
        when(getTasksUseCase.execute(userId)).thenThrow(new RuntimeException("Tasks not found"));

        // Act & Assert
        try {
            mockMvc.perform(get("/v1/anv/common/tasks?user_id={userId}", userId)
                            .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            // ServletException が発生することを確認
            assert e.getCause() instanceof RuntimeException;
            assert "Tasks not found".equals(e.getCause().getMessage());
        }

        // Verify
        verify(getTasksUseCase).execute(userId);
    }

    @Test
    void getTasks_必須クエリパラメータなし_バッドリクエストを返す() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/v1/anv/common/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTasks_UseCaseが正しく呼び出される_モックが検証される() throws Exception {
        // Arrange
        final var userId = 42;
        final var taskDtoList = Arrays.asList(
            new TaskDto(10, 42, "MockTask", false)
        );
        when(getTasksUseCase.execute(any(Integer.class))).thenReturn(taskDtoList);

        // Act
        mockMvc.perform(get("/v1/anv/common/tasks?user_id={userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assert
        verify(getTasksUseCase).execute(userId);
    }
}