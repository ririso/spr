package com.spr.application.dto;

public record GoodDto (
    Integer goodId
    ,Integer userId
    ,String goodsName
    ,String size
    ,String color
    ,Long quantity
    ,Boolean isDeleted
) {}
