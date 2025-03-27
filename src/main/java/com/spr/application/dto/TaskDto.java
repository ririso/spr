package com.spr.application.dto;

public record TaskDto (
    Integer taskId
    ,Integer userId
    ,String taskName
    ,Boolean isDeleted
) {}




