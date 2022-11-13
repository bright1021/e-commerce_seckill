package com.shmtu.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shmtu.seckill.pojo.Order;
import com.shmtu.seckill.pojo.SeckillOrder;
import com.shmtu.seckill.pojo.User;
import com.shmtu.seckill.service.IGoodsService;
import com.shmtu.seckill.service.IOrderService;
import com.shmtu.seckill.service.ISeckillOrderService;
import com.shmtu.seckill.vo.GoodsVo;

import com.shmtu.seckill.vo.RespBean;
import com.shmtu.seckill.vo.RespBeanEnum;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Controller
@Api(value = "Lightning Deals merchandise",tags = "Lightning Deals merchandise")
@RequestMapping("/seckill")
public class SecKillController {
    @Autowired
    private IGoodsService iGoodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;


//    @ApiOperation("获取秒杀地址")
//    @AccessLimit(second = 5, maxCount = 5, needLogin = true)
//    @GetMapping(value = "/path")
//    @ResponseBody
//    public RespBean getPath(TUser tuser, Long goodsId, String captcha, HttpServletRequest request) {
//        if (tuser == null) {
//            return RespBean.error(RespBeanEnum.SESSION_ERROR);
//        }
////        ValueOperations valueOperations = redisTemplate.opsForValue();
//        //限制访问次数，5秒内访问5次
////        String uri = request.getRequestURI();
////        captcha = "0";
////        Integer count = (Integer) valueOperations.get(uri + ":" + tuser.getId());
////        if (count == null) {
////            valueOperations.set(uri + ":" + tuser.getId(), 1, 5, TimeUnit.SECONDS);
////        } else if (count < 5) {
////            valueOperations.increment(uri + ":" + tuser.getId());
////        } else {
////            return RespBean.error(RespBeanEnum.ACCESS_LIMIT_REACHED);
////        }
//
//
//        boolean check = orderService.checkCaptcha(tuser, goodsId, captcha);
//        if (!check) {
//            return RespBean.error(RespBeanEnum.ERROR_CAPTCHA);
//        }
//        String str = orderService.createPath(tuser, goodsId);
//        return RespBean.success(str);
//    }

//    @ApiOperation("获取秒杀结果")
//    @GetMapping("getResult")
//    @ResponseBody
//    public RespBean getResult(TUser tUser, Long goodsId) {
//        if (tUser == null) {
//            return RespBean.error(RespBeanEnum.SESSION_ERROR);
//        }
//        Long orderId = itSeckillOrderService.getResult(tUser, goodsId);
//        return RespBean.success(orderId);
//    }

    @ApiOperation("秒杀功能")
    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSecKill(User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }

//        //优化后代码
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        boolean check = orderService.checkPath(user, goodsId, path);
//        if (!check) {
//            return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL);
//        }
//
//        //判断是否重复抢购
//        TSeckillOrder tSeckillOrder = (TSeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
//        if (tSeckillOrder != null) {
//            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
//        }
//        //内存标记，减少Redis的访问
//        if (EmptyStockMap.get(goodsId)) {
//            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
//        }
//        //预减库存
////        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
//        Long stock = (Long) redisTemplate.execute(redisScript, Collections.singletonList("seckillGoods:" + goodsId), Collections.EMPTY_LIST);
//        if (stock < 0) {
//            EmptyStockMap.put(goodsId, true);
//            valueOperations.increment("seckillGoods:" + goodsId);
//            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
//        }
//        SeckillMessage seckillMessag = new SeckillMessage(user, goodsId);
//        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessag));
//        return RespBean.success(0);


//        model.addAttribute("user", user);

        GoodsVo goodsVo = iGoodsService.findGoodsVoByGoodsId(goodsId);
        //判斷庫存
        if (goodsVo.getStockCount() < 1) {
            return RespBean.error(RespBeanEnum.ERROR_SECKILL_COUNTS);
        }
        //判断是否重复抢购
//        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id",user.getId()).eq("sku",goodsId));
//        if (seckillOrder != null) {
//            return RespBean.error(RespBeanEnum.ERROR_SECKILL_OREDER);
//        }
        String seckillOrderJson = (String)
                redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (!StringUtils.isEmpty(seckillOrderJson)) {
            return RespBean.error(RespBeanEnum.ERROR_SECKILL_OREDER);
        }
        Order order = orderService.secKill(user,goodsVo);

        if (null != order) {
            return RespBean.success(order);
        }

        return RespBean.success(order);

    }


    //跳转到订单详情页面
    @ApiOperation("秒杀功能--不用了")
    @RequestMapping("/doSeckill2")
    public String doSeckill2(Model model, User user, Long goodsSku){
        if (user == null) {
            return "login";
        }
        model.addAttribute("user",user);

        GoodsVo goodsVo = iGoodsService.findGoodsVoByGoodsId(goodsSku);
        //System.out.println(goodsVo);
        //System.out.println(user);
        Integer num =  goodsVo.getStockCount();
        //System.out.println(num);

        if (num < 1) {
            model.addAttribute("errmsg",RespBeanEnum.ERROR_SECKILL_COUNTS.getMessage());
            return "secKillFail";
        }
//        判断秒杀订单是否重复

        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id",user.getId()).eq("sku",goodsSku));
        //System.out.println(seckillOrder);
        if (seckillOrder != null) {
            model.addAttribute("errmsg",RespBeanEnum.ERROR_SECKILL_OREDER.getMessage());
            return "secKillFail";
        }
        Order order = orderService.secKill(user,goodsVo);
        model.addAttribute("order",order);
        model.addAttribute("goods",goodsVo);
        //System.out.println(order);
        return "orderDetail";
    }


}
