package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by 探险家never on 2017/1/2.
 */

public interface SuccessKillDao {
    int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}
