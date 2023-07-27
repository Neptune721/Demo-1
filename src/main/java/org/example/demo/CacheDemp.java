package org.example.demo;

import org.example.entity.Product;
import org.example.utils.JDBCUtil;
import org.example.utils.JedisUtil;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class CacheDemp {
    public static void main(String[] args) throws SQLException {
        findByID(-1);
    }
//查询商品
    public static void findByID(int id) throws SQLException {
        //1.查看缓存是非有数据
        Product product = getByRedis(id);
        if(product==null){
            System.out.println("缓存中未查询到商品");
            product=getByMySQL(id);
            if(product==null){
                System.out.println("MySQL未查询到商品");
                Product product1=new Product();
                product1.setId(id);
                saveToRedis(product1);
            }else{
                System.out.println("MySQL中查询到数据");
                saveToRedis(product);
                System.out.println(product);
            }
        }else{
            System.out.println("缓存中查询到数据");
            System.out.println(product);

        }
    }
    //从redis中获取商品
    public static   Product getByRedis(int id){
        String key="product"+id;
        Jedis jedis=JedisUtil.getJedis();
        if(jedis.exists(key)){
            String name=jedis.hget(key,"name");
            float price=Float.parseFloat(jedis.hget(key,"price"));
            int category=Integer.parseInt(jedis.hget(key,"category"));
            Product product=new Product();
            product.setId(id);
            product.setCategory(category);
            product.setName(name);
            product.setPrice(price);
            return product;
        }
        return null;
    }
    //存储商品至Redis
    public static void saveToRedis(Product product){
        Jedis jedis= JedisUtil.getJedis();
        String key="product"+product.getId();
        jedis.hset(key,"name", product.getName()+"");
        jedis.hset(key,"price",product.getPrice()+"");
        jedis.hset(key,"category",product.getCategory()+"");
        int expiredTime=3600+new Random().nextInt(100);
        jedis.expire(key,expiredTime);
    }

// 根据ID从MySQL中查询数据
    public static Product getByMySQL(int id) throws SQLException {
        Connection conn= JDBCUtil.getConnection();
        String sql="select * from product where id = ?";
        PreparedStatement statement=conn.prepareStatement(sql);
        statement.setInt(1,id);
        ResultSet resultSee=statement.executeQuery();
        Product product=null;
        if(resultSee.next()){
            float price=resultSee.getFloat("price");
            String name=resultSee.getString("name");
            int category=resultSee.getInt("category");
            product=new Product();
            product.setId(id);
            product.setCategory(category);
            product.setName(name);
            product.setPrice(price);
        }
        JDBCUtil.close(conn,statement,resultSee);
        return product;
    }
}
