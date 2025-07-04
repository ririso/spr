package com.spr.infrastructure;

import com.spr.application.dto.GoodDto;
import java.util.List;

public interface GoodsQueryDataSource {
    List<GoodDto> getGoods(Integer userId);
    GoodDto getGood(Integer goodsId);
}
