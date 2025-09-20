package com.spr.infrastructure.mapper;

import com.spr.application.dto.TaskDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.context.annotation.Import;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class TasksMapperTest {

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private TestDataManager testDataManager;

    @BeforeEach
    void setUp() {
        testDataManager.setupDefaultTasksTestData();
    }

    @AfterEach
    void tearDown() {
        testDataManager.cleanupTasksTestData();
    }

    @Test
    void getTask_should_return_task_when_task_exists() {
        Integer taskId = testDataManager.getTaskIdByName("Test Task 1");

        TaskDto task = tasksMapper.getTask(taskId);

        assertThat(task).isNotNull();
        assertThat(task.taskId()).isEqualTo(taskId);
        assertThat(task.userId()).isEqualTo(100);
        assertThat(task.taskName()).isEqualTo("Test Task 1");
        assertThat(task.isDeleted()).isFalse();
    }

    @Test
    void getTask_should_return_null_when_task_not_exists() {
        TaskDto task = tasksMapper.getTask(999);

        assertThat(task).isNull();
    }

    @Test
    void getTasks_should_return_tasks_for_user() {
        List<TaskDto> tasks = tasksMapper.getTasks(100);

        assertThat(tasks).hasSize(2); // 削除されていないタスクのみ
        assertThat(tasks).extracting("taskName")
            .containsExactlyInAnyOrder("Test Task 1", "Test Task 2");
        assertThat(tasks).extracting("userId")
            .containsOnly(100);
        assertThat(tasks).extracting("isDeleted")
            .containsOnly(false);
    }

    @Test
    void getTasks_should_return_empty_list_when_no_tasks_for_user() {
        List<TaskDto> tasks = tasksMapper.getTasks(999);

        assertThat(tasks).isEmpty();
    }

    @Test
    void getTasks_should_exclude_deleted_tasks() {
        List<TaskDto> tasks = tasksMapper.getTasks(100);

        assertThat(tasks).extracting("taskName")
            .doesNotContain("Deleted Task");
    }

    @Test
    void getTasks_should_only_return_tasks_for_specified_user() {
        List<TaskDto> user100Tasks = tasksMapper.getTasks(100);
        List<TaskDto> user200Tasks = tasksMapper.getTasks(200);

        assertThat(user100Tasks).hasSize(2);
        assertThat(user200Tasks).hasSize(1);
        assertThat(user200Tasks.get(0).taskName()).isEqualTo("Test Task 3");
    }
}