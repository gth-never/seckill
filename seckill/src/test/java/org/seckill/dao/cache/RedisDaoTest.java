package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by never on 17-4-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    @Autowired
    RedisDao redisDao;

    @Autowired
    SeckillDao seckillDao;

    @Test
    public void getTestRedisDao()throws Exception{
        long id = 1003;
        Seckill seckill = redisDao.getSeckill(id);
        if(seckill==null){
            seckill=seckillDao.queryById(id);
            String result = redisDao.setSeckill(seckill);
            System.out.println(result);
        }
        System.out.println(seckill);
    }
}