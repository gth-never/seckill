package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 探险家never on 2017/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml",
                        "classpath:spring/spring-dao.xml"})
public class SeckillServiceTest {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list=seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id=1000;
        Seckill seckill=seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id=1000;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
    }
    @Test
    public void executeSeckill() throws Exception {
        long id=1000;
        long phone=567890123;
        String md5="10bb5f8dbdd65902979f751daba7b4a5";
        SeckillExecution seckillExecution=seckillService.executeSeckill(id,phone,md5);
        logger.info("seckillExecution={}",seckillExecution);
    }
    @Test
    public void testLogic()
    {
        long id=1000;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        if(exposer.isExposed())
        {
            logger.info("exposer={}",exposer);
            long phone=123456789;
            String md5=exposer.getMd5();
            try {
                SeckillExecution seckillExecution=seckillService.executeSeckill(id,phone,md5);
                logger.info("result={}",seckillExecution);
            }catch (SeckillCloseException e)
                {
                logger.error(e.getMessage());
            }
            catch (RepeatKillException e)
            {
                logger.error(e.getMessage());
            }
        }
        else{
            logger.warn("exposer={}",exposer);
        }
    }
}