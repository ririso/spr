package com.spr.infrastructure.mapper;

import com.spr.application.dto.GoodDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GoodsMapper {
    GoodDto getGood(Integer goodId);

    List<GoodDto> getGoods(Integer userId);
}
