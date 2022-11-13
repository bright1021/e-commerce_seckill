package com.shmtu.seckill.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shmtu.seckill.exception.GlobalException;
import com.shmtu.seckill.mapper.OrderMapper;
import com.shmtu.seckill.pojo.Order;
import com.shmtu.seckill.pojo.SeckillGoods;
import com.shmtu.seckill.pojo.SeckillOrder;
import com.shmtu.seckill.pojo.User;
import com.shmtu.seckill.service.IGoodsService;
import com.shmtu.seckill.service.IOrderService;

import com.shmtu.seckill.service.ISeckillGoodsService;
import com.shmtu.seckill.service.ISeckillOrderService;
import com.shmtu.seckill.utils.JsonUtil;
import com.shmtu.seckill.vo.GoodsVo;
import com.shmtu.seckill.vo.OrderDeatilVo;
import com.shmtu.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;


/**
 * 服务实现类
 *
 * @author LiChao
 * @since 2022-03-03
 */
@Service
@Primary
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IGoodsService iGoodsService;


    @Transactional
    @Override
    public Order secKill(User user, GoodsVo goodsVo) {
        ValueOperations valueOperations = redisTemplate.opsForValue();

        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("sku", goodsVo.getSku()));

        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        //System.out.println(seckillGoods);
        //seckillGoodsService.updateById(seckillGoods);
        //boolean update = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().set("stock_count", seckillGoods.getStockCount()).eq("sku", seckillGoods.getSku()).gt("stock_count", 0));
        boolean seckillGoodsResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .set("stock_count", seckillGoods.getStockCount())
                .eq("sku", seckillGoods.getSku())
                .gt("stock_count", 0)
        );
//        boolean seckillGoodsResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
//                .setSql("stock_count = " + "stock_count-1")
//                .eq("sku", seckillGoods.getSku())
//                .gt("stock_count", 0)
//        );
        if (!seckillGoodsResult) {
            return null;
        }
//
//        if (seckillGoods.getStockCount() < 1) {
//            //判断是否还有库存
//            valueOperations.set("isStockEmpty:" + goodsVo.getSku(), "0");
//            return null;
//        }

        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setSku(goodsVo.getSku());
        order.setDeliveryAddr("xxxxxx");
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getOrderId());
        seckillOrder.setSku(goodsVo.getSku());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" +
                        goodsVo.getSku(),
                JsonUtil.object2JsonStr(seckillOrder));
        return order;
    }

    @Override
    public OrderDeatilVo detail(Long orderId) {
        if (orderId == null) {
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order tOrder = orderMapper.selectById(orderId);
        GoodsVo goodsVobyGoodsId = iGoodsService.findGoodsVoByGoodsId(tOrder.getSku());
        OrderDeatilVo orderDeatilVo = new OrderDeatilVo();
        orderDeatilVo.setOrder(tOrder);
        orderDeatilVo.setGoodsVo(goodsVobyGoodsId);
        return orderDeatilVo;
    }


}
