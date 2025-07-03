package com.spr.application.usecase;

import com.spr.application.dto.GoodDto;
import com.spr.infrastructure.GoodsQueryDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GetGoodsUseCase {
    private final GoodsQueryDataSource goodsQueryDataSource;
    public List<GoodDto> execute(Integer userId) {
        System.out.println("GetGoodsUseCase");
        return goodsQueryDataSource.getGoods(userId);
    }
}
