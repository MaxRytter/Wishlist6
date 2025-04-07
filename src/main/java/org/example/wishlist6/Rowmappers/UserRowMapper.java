package org.example.wishlist6.Rowmappers;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.wishlist6.Module.User;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        String userName = (rs.getString("user_name"));
        String userEmail =(rs.getString("user_email"));
        String userPassword = (rs.getString("password"));
        return new User(userName, userEmail, userPassword);
    }
}

