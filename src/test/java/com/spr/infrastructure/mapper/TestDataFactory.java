package com.spr.infrastructure.mapper;

import com.spr.application.dto.TaskDto;
import com.spr.application.dto.GoodDto;

public class TestDataFactory {

    public static class Tasks {

        public static TaskDto normalTask() {
            return new TaskDto(null, 100, "Test Task 1", false);
        }

        public static TaskDto normalTask2() {
            return new TaskDto(null, 100, "Test Task 2", false);
        }

        public static TaskDto otherUserTask() {
            return new TaskDto(null, 200, "Test Task 3", false);
        }

        public static TaskDto deletedTask() {
            return new TaskDto(null, 100, "Deleted Task", true);
        }

        public static TaskDto customTask(Integer userId, String taskName) {
            return new TaskDto(null, userId, taskName, false);
        }

        public static TaskDto customTask(Integer userId, String taskName, Boolean isDeleted) {
            return new TaskDto(null, userId, taskName, isDeleted);
        }
    }

    public static class Goods {

        public static GoodDto normalGood() {
            return new GoodDto(null, 100, "Test Good 1", "M", "Red", 10L, false);
        }

        public static GoodDto normalGood2() {
            return new GoodDto(null, 100, "Test Good 2", "L", "Blue", 5L, false);
        }

        public static GoodDto otherUserGood() {
            return new GoodDto(null, 200, "Test Good 3", "S", "Green", 3L, false);
        }

        public static GoodDto deletedGood() {
            return new GoodDto(null, 100, "Deleted Good", "XL", "Black", 0L, true);
        }

        public static GoodDto customGood(Integer userId, String goodsName) {
            return new GoodDto(null, userId, goodsName, "M", "Black", 1L, false);
        }

        public static GoodDto customGood(Integer userId, String goodsName, String size, String color, Long quantity, Boolean isDeleted) {
            return new GoodDto(null, userId, goodsName, size, color, quantity, isDeleted);
        }
    }
}