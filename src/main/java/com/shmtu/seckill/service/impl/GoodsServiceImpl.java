package com.shmtu.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shmtu.seckill.mapper.GoodsMapper;
import com.shmtu.seckill.pojo.Goods;
import com.shmtu.seckill.service.IGoodsService;
import com.shmtu.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bright
 */
@Service
@Primary
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    //获取商品列表
    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodsVoByGoodsId(Long sku) {
        return goodsMapper.findGoodsVoById(sku);
    }
}
