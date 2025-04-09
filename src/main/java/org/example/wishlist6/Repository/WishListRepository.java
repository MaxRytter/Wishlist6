package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Rowmappers.WishitemRowMapper;
import org.example.wishlist6.Rowmappers.WishlistRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
    // Henter alle ønskesedler
    public List<Wishlist> getAllWishlists() {
        String sql = "SELECT * FROM wishlist";
        RowMapper<Wishlist> rowMapper = new WishlistRowMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    **/

    public List<Wishlist> getWishlistsByUserId(int userId) {
        String sql = "SELECT * FROM wishlist WHERE user_id = ?";
        RowMapper<Wishlist> rowMapper = new WishlistRowMapper();
        return jdbcTemplate.query(sql, new Object[]{userId}, rowMapper);
    }



    //Gemmer ønskeseddel og returnerer genereret ID
    public int addWishlist(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (wishlist_name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, wishlist.getWishListName());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    // Gemmer et ønske til en ønskeseddel
    public void saveWish(Wishitem wish) {
        String sql = "INSERT INTO wish (wish_name, wish_description, wish_url, wishlist_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                wish.getWishItemName(),
                wish.getWishItemDescription(),
                wish.getWishUrl(), // Husk: skal matche Java-klassen
                wish.getWishlistId()
        );
    }

    public void deleteWishlistById(int id) {
        String sql = "DELETE FROM wishlist WHERE wishlist_id = ?";
        jdbcTemplate.update(sql, id);
    }


    public Wishlist getWishlistById(int id) {
        String sql = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new WishlistRowMapper());
    }

    public List<Wishitem> getWishesByWishlistId(int wishlistId) {
        String sql = "SELECT * FROM wish WHERE wishlist_id = ?";
        return jdbcTemplate.query(sql, new Object[]{wishlistId}, new WishitemRowMapper());
    }
    public void deleteWishById(int wishId) {
        String sql = "DELETE FROM wish WHERE wish_id = ?";
        jdbcTemplate.update(sql, wishId);
    }
    public Wishitem findWishById(int wishId) {
        String sql = "SELECT * FROM wish WHERE wish_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{wishId}, new WishitemRowMapper()); // Assuming you have a WishitemRowMapper
    }

    public void updateWishInfo(Wishitem wish) {
        String sql = "UPDATE wish SET wish_name = ?, wish_description = ?, wish_url = ? WHERE wish_id = ?";
        jdbcTemplate.update(sql,
                wish.getWishItemName(),
                wish.getWishItemDescription(),
                wish.getWishUrl(),
                wish.getWishItemId());
    }
    public void updateWishlist(Wishlist wishlist) {
        String sql = "UPDATE wishlist SET wishlist_name = ? WHERE wishlist_id = ?";
        jdbcTemplate.update(sql, wishlist.getWishListName(), wishlist.getWishListID());
    }



}
