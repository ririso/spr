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

CREATE TABLE Goods (
    id INT IDENTITY(1,1) PRIMARY KEY,
    userId INT NOT NULL,
    goodsName NVARCHAR(255) NOT NULL,
    size NVARCHAR(10) NOT NULL,
    color NVARCHAR(50) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    isDeleted BIT NOT NULL DEFAULT 0
);
GO

INSERT INTO Goods (userId, goodsName, size, color, quantity, isDeleted)
VALUES
    (1, 'T-shirt', 'M', 'Blue', 5, 0),
    (2, 'Jeans', 'L', 'Black', 2, 0),
    (1, 'Jacket', 'S', 'Red', 1, 1),
    (2, 'Sweater', 'XL', 'Gray', 3, 0);
GO