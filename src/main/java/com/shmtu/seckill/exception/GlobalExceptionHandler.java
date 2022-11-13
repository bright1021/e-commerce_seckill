package com.shmtu.seckill.exception;

import com.shmtu.seckill.vo.RespBean;
import com.shmtu.seckill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public RespBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException gex = (GlobalException) e;
            return RespBean.error(gex.getRespBeanEnum());
        }else if(e instanceof BindException){
            BindException bex = (BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常"+bex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }
        System.out.println("异常信息"+e);
        return RespBean.error(RespBeanEnum.ERROR);
    }

}
