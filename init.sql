CREATE DATABASE TaskDB;
GO

USE TaskDB;
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
    (N'Task 1', 0, 1),
    (N'Task 2', 1, 2),
    (N'Task 3', 0, 1);
GO