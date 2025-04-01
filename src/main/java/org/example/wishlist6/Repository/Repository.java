package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

@org.springframework.stereotype.Repository
public class Repository {

    private final JdbcTemplate jdbcTemplate;

    public Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT id, name, email, passwordhash FROM users"; // Adjust table/column names if needed

        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("passwordhash")
        );

        try {
            return jdbcTemplate.query(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No users found in the database.");
            return Collections.emptyList();
        } catch (DataAccessException e) {
            System.out.println("Database error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void save(User user) {
        String sql = "INSERT INTO users (id, name, email, passwordhash) VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, user.getUserId(), user.getUserName(), user.getUserEmail(), user.getPasswordHash());
            System.out.println("User saved successfully.");
        } catch (DataAccessException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }
}
