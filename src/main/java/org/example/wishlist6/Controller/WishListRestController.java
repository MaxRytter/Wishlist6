package org.example.wishlist6.Controller;

import org.example.wishlist6.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishListRestController {

    @Autowired
    private WishListService wishListService;

    public WishListRestController() {}

    @PostMapping("/add")
    public String addWish(
            @RequestParam String wishlistName,
            @RequestParam String wishItemName,
            @RequestParam String wishItemDesc) {
        wishListService.addWish(wishlistName, wishItemName, wishItemDesc);
        return "Wish added successfully!";
    }
//test igen
}


