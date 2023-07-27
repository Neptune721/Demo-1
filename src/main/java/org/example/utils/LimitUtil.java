package org.example.utils;

import redis.clients.jedis.Jedis;

public class LimitUtil {
    public static void  canVisit(Jedis jedis, String requestType, int limitTime, int limitCount){
        long currentTime=System.currentTimeMillis();
        jedis.zadd(requestType,currentTime,currentTime+"");
        jedis.zremrangeByScore(requestType,0,currentTime-limitTime*1000);
        long count=jedis.zcard(requestType);
        jedis.expire(requestType,limitTime+1);
        boolean flag=count<=limitCount;
        if(flag){
            System.out.println("允许访问");
        }else{
            System.out.println("限制访问");
        }
    }

    public static void main(String[] args) {
        Jedis jedis=JedisUtil.getJedis();
        for(int i=0;i<5;i++){
            canVisit(jedis,"测试模块",10,2);
        }
    }
}
