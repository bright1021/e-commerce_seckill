package com.shmtu.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author Bright
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId
    private Long sku;


    private String goodsName;


    private String goodsTitle;


    private String goodsImg;


    private String goodsDetail;


    private BigDecimal goodsPrice;


    private Integer goodsStock;


}
