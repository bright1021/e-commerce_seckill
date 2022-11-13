package com.shmtu.seckill.controller;

import com.shmtu.seckill.service.IUserService;
import com.shmtu.seckill.vo.LoginVo;
import com.shmtu.seckill.vo.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequestMapping("login")
@Slf4j
@Api(value = "登录",tags = "登录")
public class LoginController {
    @Autowired
    private IUserService iUserService;

    @ApiOperation("跳转登录页面")
    @RequestMapping(value = "/toLogin")
    public String toLogin(){
        return "login";
    }

    @ApiOperation("登录接口")
    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
//        //String id = loginVo.getMobile();
//        //String password = loginVo.getPassword();
        log.info("{}",loginVo);
        return iUserService.doLogin(loginVo,request,response);
    }

}
