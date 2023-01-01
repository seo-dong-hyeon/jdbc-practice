package org.example;

import java.sql.*;

// DAO(Data Access Object)
// 데이터베이스의 데이터에 접근하기 위해 생성하는 객체
// DB를 사용하여 데이터의 조회 및 조작하는 기능을 전담하는 오브젝트
public class UserDao {

    public void create(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        // 익명 클래스
        // 메소드 호출자(호출하는 쪽)에서 자신에게 맞게 세팅
        jdbcTemplate.executeUpdate(sql, new PreparedStatementSetter() {
            @Override
            public void setter(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, user.getUserId());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getEmail());
            }
        });
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";

        return (User) jdbcTemplate.executeQuery(sql, new PreparedStatementSetter() {
            @Override
            public void setter(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, userId);
            }
        }, new RowMapper() {
            @Override
            public Object map(ResultSet resultSet) throws SQLException {
                return new User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
            }
        });
    }
}
