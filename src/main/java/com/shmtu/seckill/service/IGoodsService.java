package com.shmtu.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shmtu.seckill.pojo.Goods;
import com.shmtu.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bright
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long sku);
}
