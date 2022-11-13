package com.shmtu.seckill.controller;

import com.shmtu.seckill.pojo.User;
import com.shmtu.seckill.rabbitmq.MQSender;
import com.shmtu.seckill.vo.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private MQSender mqSender;

    @RequestMapping(value = "/info")
    @ResponseBody
    @ApiOperation("返回用户信息")
    public RespBean info(User user) {
        return RespBean.success(user);
    }

    @RequestMapping("/mq")
    @ResponseBody
    public void mq(){
        mqSender.send("hello world");
    }
}
