package com.shmtu.seckill.vo;

import com.shmtu.seckill.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {


    private Integer stockCount;

    private BigDecimal seckillPrice;


    private Date startDate;

    private Date sendDate;


}
