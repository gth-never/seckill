package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 探险家never on 2017/1/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKillDaoTest {
    @Autowired
    private SuccessKillDao successKillDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long id=1001;
        long phone=987654321;
        int insertCount=successKillDao.insertSuccessKilled(id,phone);
        System.out.println("......"+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id=1001;
        long phone=987654321;
        SuccessKilled successKilled=successKillDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}