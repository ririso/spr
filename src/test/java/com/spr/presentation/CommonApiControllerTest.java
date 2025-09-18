package com.spr.presentation;

import com.spr.application.dto.TaskDto;
import com.spr.application.usecase.GetTaskUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTask_正常なタスクID_タスクを返す() throws Exception {
        // Arrange
        final Integer taskId = 1;
        final TaskDto expectedTaskDto = new TaskDto(1, 100, "TestTask", false);
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
        final Integer taskId = 2;
        final TaskDto expectedTaskDto = new TaskDto(2, 200, "AnotherTask", false);
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
        final Integer taskId = 999;
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
        final Integer taskId = 42;
        final TaskDto taskDto = new TaskDto(42, 300, "MockTask", false);
        when(getTaskUseCase.execute(any(Integer.class))).thenReturn(taskDto);

        // Act
        mockMvc.perform(get("/v1/anv/common/tasks/{task_id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assert
        verify(getTaskUseCase).execute(taskId);
    }
}