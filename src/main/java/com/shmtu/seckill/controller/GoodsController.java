package com.shmtu.seckill.controller;

import com.shmtu.seckill.pojo.User;


import com.shmtu.seckill.service.IGoodsService;
import com.shmtu.seckill.service.IUserService;
import com.shmtu.seckill.vo.DetailVo;
import com.shmtu.seckill.vo.GoodsVo;
import com.shmtu.seckill.vo.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IGoodsService iGoodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @ApiOperation("a list of products")
    @RequestMapping(value = "/toList",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        //如果为空，手动渲染
        model.addAttribute("user", user);
        model.addAttribute("goodsList", iGoodsService.findGoodsVo());

        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }

//        if(StringUtils.isEmpty(cookie)){
//            return "login";
//        }
//
//        //User user = (User)session.getAttribute(cookie);
//        User user = iUserService.getUserByCookie(cookie, request, response);
//        if(user==null){
//            return "login";
//        }
//        model.addAttribute("user",user);
//        model.addAttribute("goodsList",iGoodsService.findGoodsVo());

        //return "goodsList";
        return html;

    }

    @ApiOperation("Product details")
    @RequestMapping(value = "/toDetail/{goodsId}",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(Model model,User user, @PathVariable Long goodsId,
                           HttpServletRequest request, HttpServletResponse response) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }


        GoodsVo goodsVo = iGoodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getSendDate();
        Date nowDate = new Date();
        //秒杀状态
       int seckillStatus = 0;
       //秒杀倒计时
       int remainSeconds = 0;

      if (nowDate.before(startDate)) {
            //秒杀还未开始0
          remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.after(endDate)) {
          //秒杀已经结束
           seckillStatus = 2;
           remainSeconds = -1;
        } else {
            //秒杀进行中
           seckillStatus = 1;
           remainSeconds = 0;
       }

        model.addAttribute("user", user);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("goods", goodsVo);
        model.addAttribute("seckillStatus", seckillStatus);

        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsDetail:" + goodsId, html, 60, TimeUnit.SECONDS);
        }

        return html;
    }

    @ApiOperation("商品详情")
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(User user, @PathVariable Long goodsId) {
        GoodsVo goodsVo = iGoodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getSendDate();
        Date nowDate = new Date();
        //秒杀状态
        int seckillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;

        if (nowDate.before(startDate)) {
            //秒杀还未开始0
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.after(endDate)) {
            //秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }

        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setSecKillStatus(seckillStatus);
        //System.out.println(detailVo.getGoodsVo());
        //System.out.println(RespBean.success(detailVo));
        return RespBean.success(detailVo);
    }


}
