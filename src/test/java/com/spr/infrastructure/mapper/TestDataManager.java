package com.spr.infrastructure.mapper;

import com.spr.application.dto.TaskDto;
import com.spr.application.dto.GoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

public class TestDataManager {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Tasks テーブル関連

    public void setupDefaultTasksTestData() {
        cleanupTasksTestData();

        insertTasks(
            TestDataFactory.Tasks.normalTask(),
            TestDataFactory.Tasks.normalTask2(),
            TestDataFactory.Tasks.otherUserTask(),
            TestDataFactory.Tasks.deletedTask()
        );
    }

    public void insertTasks(TaskDto... tasks) {
        for (TaskDto task : tasks) {
            insertTask(task);
        }
    }

    public Integer insertTask(TaskDto task) {
        String sql = """
            INSERT INTO Tasks (userId, taskName, isDeleted)
            VALUES (?, ?, ?)
            """;

        jdbcTemplate.update(sql, task.userId(), task.taskName(), task.isDeleted() ? 1 : 0);

        return jdbcTemplate.queryForObject(
            "SELECT id FROM Tasks WHERE userId = ? AND taskName = ? ORDER BY id DESC",
            Integer.class,
            task.userId(), task.taskName()
        );
    }

    public Integer getTaskIdByName(String taskName) {
        return jdbcTemplate.queryForObject(
            "SELECT id FROM Tasks WHERE taskName = ?",
            Integer.class,
            taskName
        );
    }

    public List<Integer> getTaskIdsByUserId(Integer userId) {
        return jdbcTemplate.queryForList(
            "SELECT id FROM Tasks WHERE userId = ? AND isDeleted = 0 ORDER BY id",
            Integer.class,
            userId
        );
    }

    public void cleanupTasksTestData() {
        jdbcTemplate.update("DELETE FROM Tasks WHERE userId IN (100, 200)");
    }

    // Goods テーブル関連

    public void setupDefaultGoodsTestData() {
        cleanupGoodsTestData();

        insertGoods(
            TestDataFactory.Goods.normalGood(),
            TestDataFactory.Goods.normalGood2(),
            TestDataFactory.Goods.otherUserGood(),
            TestDataFactory.Goods.deletedGood()
        );
    }

    public void insertGoods(GoodDto... goods) {
        for (GoodDto good : goods) {
            insertGood(good);
        }
    }

    public Integer insertGood(GoodDto good) {
        // まず Goods テーブルが存在するかチェック
        try {
            jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Goods", Integer.class);
        } catch (Exception e) {
            // Goods テーブルが存在しない場合は作成
            createGoodsTableIfNotExists();
        }

        String sql = """
            INSERT INTO Goods (userId, goodsName, size, color, quantity, isDeleted)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.update(sql,
            good.userId(),
            good.goodsName(),
            good.size(),
            good.color(),
            good.quantity(),
            good.isDeleted() ? 1 : 0
        );

        return jdbcTemplate.queryForObject(
            "SELECT id FROM Goods WHERE userId = ? AND goodsName = ? ORDER BY id DESC",
            Integer.class,
            good.userId(), good.goodsName()
        );
    }

    public Integer getGoodIdByName(String goodsName) {
        return jdbcTemplate.queryForObject(
            "SELECT id FROM Goods WHERE goodsName = ?",
            Integer.class,
            goodsName
        );
    }

    public List<Integer> getGoodIdsByUserId(Integer userId) {
        return jdbcTemplate.queryForList(
            "SELECT id FROM Goods WHERE userId = ? AND isDeleted = 0 ORDER BY id",
            Integer.class,
            userId
        );
    }

    public void cleanupGoodsTestData() {
        try {
            jdbcTemplate.update("DELETE FROM Goods WHERE userId IN (100, 200)");
        } catch (Exception e) {
            // Goods テーブルが存在しない場合は何もしない
        }
    }

    private void createGoodsTableIfNotExists() {
        String createTableSql = """
            IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Goods' AND xtype='U')
            CREATE TABLE Goods (
                id INT IDENTITY(1,1) PRIMARY KEY,
                userId INT NOT NULL,
                goodsName NVARCHAR(255) NOT NULL,
                size NVARCHAR(10),
                color NVARCHAR(50),
                quantity BIGINT DEFAULT 0,
                isDeleted BIT DEFAULT 0
            )
            """;

        jdbcTemplate.execute(createTableSql);
    }

    // 全体のクリーンアップ

    public void cleanupAllTestData() {
        cleanupTasksTestData();
        cleanupGoodsTestData();
    }

    public void setupAllDefaultTestData() {
        setupDefaultTasksTestData();
        setupDefaultGoodsTestData();
    }
}