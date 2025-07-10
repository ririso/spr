package com.spr.application.usecase;

import com.spr.application.dto.GoodDto;
import com.spr.infrastructure.GoodsQueryDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GetGoodUseCase {
    private final GoodsQueryDataSource goodsQueryDataSource;
    public GoodDto execute(Integer goodsId) {
        return goodsQueryDataSource.getGood(goodsId);
    }
}
