package org.example.demo;

import org.example.utils.LimitByLuaUtil;
import redis.clients.jedis.Jedis;

public class RequestDemo extends Thread{
    @Override
    public void run(){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        for(int i=0;i<5;i++){
            String name="支付模块";
            boolean r=LimitByLuaUtil.canVisit(jedis,name,3,10);
            if(r){
                System.out.println(name+"可以访问");
            }else{
                System.out.println(name+"限制访问");
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<2;i++){
            new RequestDemo().start();
        }
    }
}
