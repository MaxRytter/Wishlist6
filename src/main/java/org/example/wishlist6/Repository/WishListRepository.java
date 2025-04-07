package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
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

    // Henter alle ønskesedler
    public List<Wishlist> getAllWishlists() {
        String sql = "SELECT * FROM wishlist";
        RowMapper<Wishlist> rowMapper = new WishlistRowMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Gemmer ønskeseddel og returnerer genereret ID
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
    public void saveWish(int wishlistId, Wishitem wish) {
        String sql = "INSERT INTO wish (wish_name, wish_description, wish_price, wish_url, wishlist_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                wish.getWishItemName(),
                wish.getWishItemDescription(),
                wish.getWishItemPrice(),
                wish.getWishUrl(),  // Husk: skal matche Java-klassen
                wishlistId
        );
    }

    // Finder et specifikt ønske (bruges f.eks. til redigering)
    public Wishitem getWishById(int id) {
        String sql = "SELECT * FROM wish WHERE wish_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Wishitem wishItem = new Wishitem();
                wishItem.setWishItemName(rs.getString("wish_name"));
                wishItem.setWishItemDescription(rs.getString("wish_description"));
                wishItem.setWishItemPrice(rs.getDouble("wish_price"));
                wishItem.setWishUrl(rs.getString("wish_url"));
                return wishItem;
            });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Wishitem with ID " + id + " not found.");
            return null;
        }
    }

    // Opdaterer et ønske
    public void updateWish(int id, Wishitem wishItem) {
        String sql = "UPDATE wish SET wish_name = ?, wish_description = ?, wish_price = ?, wish_url = ? WHERE wish_id = ?";
        jdbcTemplate.update(sql,
                wishItem.getWishItemName(),
                wishItem.getWishItemDescription(),
                wishItem.getWishItemPrice(),
                wishItem.getWishUrl(),
                id
        );
    }

    // Sletter et ønske
    public void removeWish(int id) {
        String sql = "DELETE FROM wish WHERE wish_id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Bruges ikke pt, men kan udvides til at vise ønsker
    public List<Wishlist> findAll() {
        return getAllWishlists();
    }

    // Tilføjer bruger
    public void saveUser(User user) {
        String sql = "INSERT INTO users (user_name, user_email, user_password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getUserName(),
                user.getUserEmail(),
                user.getUserPassword()
        );
    }

    public void save(Wishlist wishlist) {
    }
}
