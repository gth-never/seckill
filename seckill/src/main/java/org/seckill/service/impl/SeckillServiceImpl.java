package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by 探险家never on 2017/1/5.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKillDao successKillDao;
    private RedisDao redisDao;
    //md5盐值字符串 用于混淆md5
    private final String slat="asdfghjkl";
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //优化点：redis缓存。
        //访问redis
        Seckill seckill=redisDao.getSeckill(seckillId);

        //如果结果存在且redis中没有则放入一个
        if(seckill==null){
            //访问数据库
            seckill=seckillDao.queryById(seckillId);
            if(seckill==null){
                return new Exposer(false,seckillId);
            }
            //放入redis
            redisDao.setSeckill(seckill);
        }
        Date nowTime=new Date();
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime())
        {
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }
    private String getMD5(long seckillId)
    {
        String base=seckillId+"/"+slat;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());//利用工具产生md5
        return md5;
    }
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException {
        if(md5==null||!md5.equals(getMD5(seckillId)))
        {
            throw new SeckillException("seckill data rewrite");
        }
        Date nowTime=new Date();
        try {
            //减库存
                int updateCount=seckillDao.reduceNumber(seckillId,nowTime);
                if(updateCount==0)
                {
                    throw new SeckillCloseException("seckill is closed");
                }
                else {
                    //插入秒杀记录
                    int insertCount = successKillDao.insertSuccessKilled(seckillId, userPhone);
                    if (insertCount == 0) {
                        throw new RepeatKillException("seckill repeat");
                    } else {
                        SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(seckillId, userPhone);
                        return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
                    }
                }
        }
        catch (SeckillCloseException e1)
        {
            throw e1;
        }
        catch (RepeatKillException e2)
        {
            throw e2;
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
            throw new SeckillException("seckill inner error"+e.getMessage());
        }
    }
}
