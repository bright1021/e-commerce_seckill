package com.shmtu.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_seckill_order")
public class SeckillOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long seckillOrderId;

    private Long userId;

    private Long sku;

    private Long orderId;


}
