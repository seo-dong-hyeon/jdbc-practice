package org.example;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    public static final String DB_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:mem://localhost/~/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
    public static final String DB_USERNAME = "sa";
    public static final String DB_PW = "";
    private static int MAX_POOL_SIZE = 40;

    // 커넥션 풀을 하나만 가지도록
    private static final DataSource ds;

    // static 초기화
    static {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(DB_DRIVER);
        hikariDataSource.setJdbcUrl(DB_URL);
        hikariDataSource.setUsername(DB_USERNAME);
        hikariDataSource.setPassword(DB_PW);
        hikariDataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        hikariDataSource.setMaximumPoolSize(MAX_POOL_SIZE);

        ds = hikariDataSource;
    }

    // 커넥션을 받아오는 부분에 커넥스 풀 적용
    public static Connection getConnection() {
        try{
            return ds.getConnection();
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }

    public static DataSource getDataSource(){
        return ds;
    }
}
