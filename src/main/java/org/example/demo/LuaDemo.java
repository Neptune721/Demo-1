package org.example.demo;

import redis.clients.jedis.Jedis;

public class LuaDemo {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        String lua="redis.call('set',KEYS[1],ARGV[1])";
        jedis.eval(lua,1,"age","33");
        System.out.println(jedis.get("age"));
    }
}
