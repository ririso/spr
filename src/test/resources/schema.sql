-- テスト用のテーブル作成
CREATE TABLE IF NOT EXISTS Tasks (
    id INT PRIMARY KEY,
    userId INT NOT NULL,
    taskName NVARCHAR(255) NOT NULL,
    isDeleted BIT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS Goods (
    id INT PRIMARY KEY,
    userId INT NOT NULL,
    goodsName NVARCHAR(255) NOT NULL,
    size NVARCHAR(10),
    color NVARCHAR(50),
    quantity BIGINT DEFAULT 0,
    isDeleted BIT DEFAULT 0
);