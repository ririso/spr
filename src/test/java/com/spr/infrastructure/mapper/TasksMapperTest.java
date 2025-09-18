package com.spr.infrastructure.mapper;

import com.spr.application.dto.TaskDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TasksMapperTest {

    @Autowired
    private TasksMapper tasksMapper;

    @Test
    @Sql("/sql/tasks-test-data.sql")
    void getTask_should_return_task_when_task_exists() {
        TaskDto task = tasksMapper.getTask(1);

        assertThat(task).isNotNull();
        assertThat(task.taskId()).isEqualTo(1);
        assertThat(task.userId()).isEqualTo(100);
        assertThat(task.taskName()).isEqualTo("Test Task 1");
        assertThat(task.isDeleted()).isFalse();
    }

    @Test
    @Sql("/sql/tasks-test-data.sql")
    void getTask_should_return_null_when_task_not_exists() {
        TaskDto task = tasksMapper.getTask(999);

        assertThat(task).isNull();
    }

    @Test
    @Sql("/sql/tasks-test-data.sql")
    void getTasks_should_return_tasks_for_user() {
        List<TaskDto> tasks = tasksMapper.getTasks(100);

        assertThat(tasks).hasSize(2);
        assertThat(tasks).extracting("taskName").containsExactlyInAnyOrder("Test Task 1", "Test Task 2");
    }

    @Test
    @Sql("/sql/tasks-test-data.sql")
    void getTasks_should_return_empty_list_when_no_tasks_for_user() {
        List<TaskDto> tasks = tasksMapper.getTasks(999);

        assertThat(tasks).isEmpty();
    }
}