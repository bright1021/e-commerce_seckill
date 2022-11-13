package com.shmtu.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 秒杀商品表
 *
 */
@TableName("t_seckill_goods")
@ApiModel(value = "秒杀商品表", description = "秒杀商品表")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 商品ID **/
    @TableId
    @ApiModelProperty("商品ID")
    private Long sku;

    /** 秒杀家 **/
    @ApiModelProperty("秒杀价格")
    private BigDecimal seckillPrice;

    /** 库存数量 **/
    @ApiModelProperty("库存数量")
    private Integer stockCount;

    /** 秒杀开始时间 **/
    @ApiModelProperty("秒杀开始时间")
    private LocalDateTime startDate;

    /** 秒杀结束时间 **/
    @ApiModelProperty("秒杀结束时间")
    private LocalDateTime sendDate;



}
