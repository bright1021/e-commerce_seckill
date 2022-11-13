package com.shmtu.seckill.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shmtu.seckill.mapper.SeckillGoodsMapper;
import com.shmtu.seckill.pojo.SeckillGoods;
import com.shmtu.seckill.service.ISeckillGoodsService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 秒杀商品表 服务实现类
 *
 */
@Service
@Primary
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements ISeckillGoodsService {

}
