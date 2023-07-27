package org.example.demo;

import org.example.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MYSQLdemo {
    public static void main(String[] args) throws SQLException {
        Connection conn= JDBCUtil.getConnection();
        String sql="select * from product";
        PreparedStatement statement=conn.prepareStatement(sql);
        ResultSet resultSee=statement.executeQuery();
        while(resultSee.next()){
            int id=resultSee.getInt("id");
            float price=resultSee.getFloat("price");
            String name=resultSee.getString("name");
            int category=resultSee.getInt("category");
            System.out.println("id:"+id+" name:"+name+" price:"+price+" category:"+category);
        }
        JDBCUtil.close(conn,statement,resultSee);
    }
}
