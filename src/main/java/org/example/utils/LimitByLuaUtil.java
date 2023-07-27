package org.example.utils;

import redis.clients.jedis.Jedis;

public class LimitByLuaUtil {

    private static String script="local obj = KEYS[1]\n"+
            "local limitNum = tonumber(ARGV[1])\n"+
            "local curVisitNum = tonumber(redis.call('get',obj) or '0')\n"+
            "if curVisitNum == limitNum then\n"+
            "     return 0\n"+
            "else\n"+
            "      redis.call('incrby',obj,'1')\n" +
            "      redis.call('expire',obj,ARGV[2])\n" +
            "      return curVisitNum+1 \n"+
            "end";
    public static boolean canVisit(Jedis jedis, String model, int limitNum, int limitTime){
        String r=jedis.eval(script,1,model,limitNum+"",limitTime+"").toString();
        return !"0".equals(r);
    }
}
