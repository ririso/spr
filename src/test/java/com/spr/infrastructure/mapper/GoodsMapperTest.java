package com.spr.infrastructure.mapper;

import com.spr.application.dto.GoodDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class GoodsMapperTest {

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    @Sql("/sql/goods-test-data.sql")
    void getGood_should_return_good_when_good_exists() {
        GoodDto good = goodsMapper.getGood(1);

        assertThat(good).isNotNull();
        assertThat(good.goodId()).isEqualTo(1);
        assertThat(good.userId()).isEqualTo(100);
        assertThat(good.goodsName()).isEqualTo("Test Good 1");
        assertThat(good.size()).isEqualTo("M");
        assertThat(good.color()).isEqualTo("Red");
        assertThat(good.quantity()).isEqualTo(10L);
        assertThat(good.isDeleted()).isFalse();
    }

    @Test
    @Sql("/sql/goods-test-data.sql")
    void getGood_should_return_null_when_good_not_exists() {
        GoodDto good = goodsMapper.getGood(999);

        assertThat(good).isNull();
    }

    @Test
    @Sql("/sql/goods-test-data.sql")
    void getGoods_should_return_goods_for_user() {
        List<GoodDto> goods = goodsMapper.getGoods(100);

        assertThat(goods).hasSize(2);
        assertThat(goods).extracting("goodsName").containsExactlyInAnyOrder("Test Good 1", "Test Good 2");
    }

    @Test
    @Sql("/sql/goods-test-data.sql")
    void getGoods_should_return_empty_list_when_no_goods_for_user() {
        List<GoodDto> goods = goodsMapper.getGoods(999);

        assertThat(goods).isEmpty();
    }
}