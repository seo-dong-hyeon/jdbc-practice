package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 라이브러리
public class JdbcTemplate {

    public void executeUpdate(String sql, PreparedStatementSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = ConnectionManager.getConnection();
            preparedStatement = con.prepareStatement(sql);
            pss.setter(preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            // 생성 순서 거꾸로 해제
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (con != null){
                con.close();
            }
        }
    }

    public Object executeQuery(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getConnection();
            preparedStatement = con.prepareStatement(sql);
            pss.setter(preparedStatement);
            rs = preparedStatement.executeQuery();

            Object obj = null;
            if (rs.next()){
                return rowMapper.map(rs);
            }

            return obj;
        }finally {
            // 생성 순서 거꾸로 해제
            if (rs != null){
                rs.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (con != null){
                con.close();
            }
        }
    }
}
