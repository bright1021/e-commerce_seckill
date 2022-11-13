package com.shmtu.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    SUCCESS(200,"Success"),
    ERROR(500,"服务异常"),

    //login
    LOGIN_ERROR_EMPTY(500200,"用户名或密码不为空"),
    LOGIN_ERROR(500210,"用户名或密码错误"),
    BIND_ERROR(500220,"参数校验异常"),

    SESSION_ERROR(500221,"用户不存在"),

    PASSWORD_UPDATE_FAIL(500230,"密码更新失败"),

    //Lightning Deals merchandise
    ERROR_SECKILL_COUNTS(600620,"没有名额"),

    ERROR_SECKILL_OREDER(600621,"只能抢购一件商品"),

    ORDER_NOT_EXIST(700720,"订单不存在");
    private final Integer code;
    private final String message;


}
