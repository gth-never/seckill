package org.seckill.dao.cache;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Created by never on 17-4-4.
 */
public class RedisDao {
    private final JedisPool jedisPool;
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);
    public RedisDao(String ip,int port)
    {
        jedisPool=new JedisPool(ip,port);
    }
    public Seckill getSeckill(long id){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            String key = "seckill:"+id;
            byte[] bytes=jedis.get(key.getBytes());
            if (bytes!=null){
                //得到空对象
                Seckill seckill = schema.newMessage();
                //将字节数组反序列化为对象（按照模式）
                ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                return seckill;
            }
        }catch (Exception e){
            logger.error("redis错误！",e);
        }
        finally{
            if(jedis!=null)
                jedis.close();
        }
        return null;
    }
    public String setSeckill(Seckill seckill){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String key = "seckill:"+seckill.getSeckillId();
            //序列化
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));//对象很大的时候可以缓冲
            //存入redis
            String result = jedis.setex(key.getBytes(),60*60,bytes);
            return result;
        }catch (Exception e){
            logger.error("redis错误！",e);
        } finally {
            if(jedis!=null)
                jedis.close();
        }
        return null;
    }

}
