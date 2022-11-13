package com.shmtu.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/demo")
public class DemoController {
    /**
     * 功能测试：页面跳转
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String Hello(HttpServletRequest request,Model model){
        request.setAttribute("age",26);
        model.addAttribute("name","常亮");
        return "hello";
    }

}
