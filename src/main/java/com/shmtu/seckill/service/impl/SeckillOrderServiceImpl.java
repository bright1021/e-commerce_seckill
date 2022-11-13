package com.shmtu.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.shmtu.seckill.mapper.SeckillOrderMapper;


import com.shmtu.seckill.pojo.SeckillOrder;
import com.shmtu.seckill.service.ISeckillOrderService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {
}
