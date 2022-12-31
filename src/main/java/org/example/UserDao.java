package org.example;

import java.sql.*;

// DAO(Data Access Object)
// 데이터베이스의 데이터에 접근하기 위해 생성하는 객체
// DB를 사용하여 데이터의 조회 및 조작하는 기능을 전담하는 오브젝트
public class UserDao {
    public void create(User user) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = ConnectionManager.getConnection();
            String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
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

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userId);

            rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()){
                user = new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }

            return user;
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
