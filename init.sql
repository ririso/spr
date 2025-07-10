CREATE DATABASE anvDB;
GO

USE anvDB;
GO

CREATE TABLE Tasks (
    id INT IDENTITY(1,1) PRIMARY KEY,
    userId INT NOT NULL,
    taskName NVARCHAR(255) NOT NULL,
    isDeleted BIT NOT NULL DEFAULT 0
);
GO

INSERT INTO Tasks (taskName, isDeleted, userId)
VALUES
    ('JavaTask', 0, 1),
    ('ScalaTask', 1, 2),
    ('PythonTask', 0, 1);

CREATE TABLE Users (
    userId INT IDENTITY(1,1) PRIMARY KEY,
    userName VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    isDeleted BIT NOT NULL DEFAULT 0,
    team VARCHAR(20) NOT NULL DEFAULT 'development'
);
GO

INSERT INTO Users (userName, age, isDeleted, team)
VALUES
    ('johnny_joestar', 20, 0, 'development'),       -- ジョニィ・ジョースター
    ('gyro_zeppeli', 26, 0, 'development'),         -- ジャイロツェペリ
    ('fanny_vallar', 35, 0, 'infrastructure'),      -- ファニーヴァレンタイン
    ('diego_brando', 25, 1, 'infrastructure'),      -- ディエゴ・ブランドー
    ('mountain_tim', 40, 0, 'development'),         -- マウンテン・ティム
    ('ringo_road_again', 30, 0, 'development'),     -- リンゴォロードアゲイン
    ('norisuke_higashikata', 22, 0, 'development')  -- ノリスケ・ヒガシカタ
GO