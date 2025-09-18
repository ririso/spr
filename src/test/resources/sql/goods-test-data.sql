-- テスト用のGoodsテーブルのテストデータ
INSERT INTO Goods (id, userId, goodsName, size, color, quantity, isDeleted) VALUES
(1, 100, 'Test Good 1', 'M', 'Red', 10, 0),
(2, 100, 'Test Good 2', 'L', 'Blue', 5, 0),
(3, 200, 'Test Good 3', 'S', 'Green', 3, 0),
(4, 100, 'Deleted Good', 'XL', 'Black', 0, 1);