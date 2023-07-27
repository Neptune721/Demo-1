package org.example.utils;

import java.sql.*;

public class JDBCUtil {

    private static String driver="com.mysql.cj.jdbc.Driver";
    //        2.用户信息和url
    private static String url = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
    private static String username="root";
    private static String password="123456";

    static{
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        Connection conn=null;
        try {
            conn=DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    public static void close(Connection conn, PreparedStatement statement, ResultSet resultSet){
        if(resultSet!=null){
            try{
                resultSet.close();
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }
         if(statement!=null){
             try{
                 statement.close();
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
         if(conn!=null){
             try{
                 conn.close();
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
    }
}
