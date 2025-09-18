-- テスト用のTasksテーブルのテストデータ
INSERT INTO Tasks (id, userId, taskName, isDeleted) VALUES
(1, 100, 'Test Task 1', 0),
(2, 100, 'Test Task 2', 0),
(3, 200, 'Test Task 3', 0),
(4, 100, 'Deleted Task', 1);