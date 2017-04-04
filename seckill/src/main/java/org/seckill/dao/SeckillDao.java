package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by 探险家never on 2017/1/2.
 */

public interface SeckillDao {
    int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
    Seckill queryById(long seckillId);
    //java中没有保存形参的记录
    List<Seckill> queryAll(@Param("offset")int offset, @Param("limit")int limit);
}
