package org.example.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class Main {
    public static void main(String[] args) {

        Jedis   jedis=new Jedis("127.0.0.1",6379);
        jedis.auth("123456");
        System.out.println(jedis.ping());
        System.out.println(jedis.set("name","曹晓慈"));
        System.out.println(jedis.get("name"));

        System.out.println(jedis.exists("name"));
        System.out.println(jedis.del("name"));

        Transaction t1 =jedis.multi();
        t1.set("score","90");
        t1.set("age","22");
        t1.exec();
    }
}
