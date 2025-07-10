package com.spr.infrastructure;

import com.spr.application.dto.GoodDto;
import com.spr.infrastructure.mapper.GoodsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoodsQueryDataSourceImpl implements GoodsQueryDataSource {
    private final GoodsMapper goodsMapper;

    @Override
    public List<GoodDto> getGoods(final Integer userId) {
        System.out.println("getGoods");
        final var result = goodsMapper.getGoods(userId);
        System.out.println("result" + result);
        return result;
    }

    @Override
    public GoodDto getGood(final Integer goodId) {
        System.out.println("GoodsQueryDataSourceImpl");
        System.out.println("goodsMapper is null? " + (goodsMapper == null));
        System.out.println("goodsMapper? " + goodsMapper );

        return goodsMapper.getGood(goodId);
    }
}
