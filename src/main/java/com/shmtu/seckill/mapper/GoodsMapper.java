package com.shmtu.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shmtu.seckill.pojo.Goods;
import com.shmtu.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bright
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    //获取商品列表
    List<GoodsVo> findGoodsVo();

    //获取商品详情
    GoodsVo findGoodsVoById(Long sku);


}
