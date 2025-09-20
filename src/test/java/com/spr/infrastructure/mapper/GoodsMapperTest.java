package com.spr.infrastructure.mapper;

import com.spr.application.dto.GoodDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.context.annotation.Import;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class GoodsMapperTest {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private TestDataManager testDataManager;

    @BeforeEach
    void setUp() {
        testDataManager.setupDefaultGoodsTestData();
    }

    @AfterEach
    void tearDown() {
        testDataManager.cleanupGoodsTestData();
    }

    @Test
    void getGood_should_return_good_when_good_exists() {
        Integer goodId = testDataManager.getGoodIdByName("Test Good 1");

        GoodDto good = goodsMapper.getGood(goodId);

        assertThat(good).isNotNull();
        assertThat(good.goodId()).isEqualTo(goodId);
        assertThat(good.userId()).isEqualTo(100);
        assertThat(good.goodsName()).isEqualTo("Test Good 1");
        assertThat(good.size()).isEqualTo("M");
        assertThat(good.color()).isEqualTo("Red");
        assertThat(good.quantity()).isEqualTo(10L);
        assertThat(good.isDeleted()).isFalse();
    }

    @Test
    void getGood_should_return_null_when_good_not_exists() {
        GoodDto good = goodsMapper.getGood(999);

        assertThat(good).isNull();
    }

    @Test
    void getGoods_should_return_goods_for_user() {
        List<GoodDto> goods = goodsMapper.getGoods(100);

        assertThat(goods).hasSize(2); // 削除されていない商品のみ
        assertThat(goods).extracting("goodsName")
            .containsExactlyInAnyOrder("Test Good 1", "Test Good 2");
        assertThat(goods).extracting("userId")
            .containsOnly(100);
        assertThat(goods).extracting("isDeleted")
            .containsOnly(false);
    }

    @Test
    void getGoods_should_return_empty_list_when_no_goods_for_user() {
        List<GoodDto> goods = goodsMapper.getGoods(999);

        assertThat(goods).isEmpty();
    }

    @Test
    void getGoods_should_exclude_deleted_goods() {
        List<GoodDto> goods = goodsMapper.getGoods(100);

        assertThat(goods).extracting("goodsName")
            .doesNotContain("Deleted Good");
    }

    @Test
    void getGoods_should_only_return_goods_for_specified_user() {
        List<GoodDto> user100Goods = goodsMapper.getGoods(100);
        List<GoodDto> user200Goods = goodsMapper.getGoods(200);

        assertThat(user100Goods).hasSize(2);
        assertThat(user200Goods).hasSize(1);
        assertThat(user200Goods.get(0).goodsName()).isEqualTo("Test Good 3");
    }

    @Test
    void getGoods_should_return_correct_attributes() {
        List<GoodDto> goods = goodsMapper.getGoods(100);

        GoodDto testGood1 = goods.stream()
            .filter(g -> "Test Good 1".equals(g.goodsName()))
            .findFirst()
            .orElseThrow();

        assertThat(testGood1.size()).isEqualTo("M");
        assertThat(testGood1.color()).isEqualTo("Red");
        assertThat(testGood1.quantity()).isEqualTo(10L);

        GoodDto testGood2 = goods.stream()
            .filter(g -> "Test Good 2".equals(g.goodsName()))
            .findFirst()
            .orElseThrow();

        assertThat(testGood2.size()).isEqualTo("L");
        assertThat(testGood2.color()).isEqualTo("Blue");
        assertThat(testGood2.quantity()).isEqualTo(5L);
    }
}