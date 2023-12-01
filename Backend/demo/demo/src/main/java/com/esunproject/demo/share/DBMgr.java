package com.esunproject.demo.share;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMgr {
    //db 連線參數
    private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/mydb";
    private static String dbUsername = "root";
    private static String dbPassword = "root";
    // 建立db連線
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}
