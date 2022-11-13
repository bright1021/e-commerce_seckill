package com.shmtu.seckill.controller;

import com.shmtu.seckill.pojo.User;
import com.shmtu.seckill.service.IOrderService;
import com.shmtu.seckill.vo.OrderDeatilVo;
import com.shmtu.seckill.vo.RespBean;
import com.shmtu.seckill.vo.RespBeanEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
@Api(value = "订单", tags = "订单")
public class OrderController {

    @Autowired
    private IOrderService orderService;


    @ApiOperation("订单")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public RespBean detail(User user, Long orderId) {
        //System.out.println(orderId);
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDeatilVo orderDeatilVo = orderService.detail(orderId);
        System.out.println(orderDeatilVo.getGoodsVo());
        System.out.println(orderDeatilVo.getOrder());
        return RespBean.success(orderDeatilVo);
    }
}
