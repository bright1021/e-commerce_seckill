package com.shmtu.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shmtu.seckill.pojo.Order;
import com.shmtu.seckill.pojo.User;
import com.shmtu.seckill.vo.GoodsVo;
import com.shmtu.seckill.vo.OrderDeatilVo;


/**
 * 服务类
 *
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     *
     * @param user    用户对象
     * @param goodsVo 商品对象
     * @return com.example.seckilldemo.entity.TOrder
     * @author LC
     * @operation add
     * @date 1:44 下午 2022/3/4
     **/
    Order secKill(User user, GoodsVo goodsVo);


    OrderDeatilVo detail(Long orderId);

}
