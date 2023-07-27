package org.example.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
    private static JedisPool pool;
    static {
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(10); //最大连接数
        config.setMaxIdle(5);  //最大空闲连接数
        config.setMaxWaitMillis(100);//最大等待时间 毫秒
        config.setBlockWhenExhausted(false);//连接耗尽时不阻塞
        //创建连接池
        pool=new JedisPool(config,"127.0.0.1",6379);
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }
}
