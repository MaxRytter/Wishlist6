package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@org.springframework.stereotype.Controller
public class WishListRestController {

    @Autowired
    private WishListService wishListService;

    public WishListRestController() {}

    @GetMapping("/")
    public String showFrontPage() {
        return "index";
    }

    @GetMapping("/wishlist")
    public String getWishlist() {
        return "index";
    }

    @GetMapping("/wishlist/create")
    public String showCreateForm(Model model) {
        model.addAttribute("wishlist", new Wishlist());
        return "create-wishlist";
    }

    @PostMapping("/wishlist/create")
    public String createWishlist(@ModelAttribute Wishlist wishlist) {
        wishListService.addWishlist(wishlist);
        return "redirect:/wishlist";
    }


    @GetMapping("/wishlist/{id}")
    public String getWishlistByID() {
        return "index";
    }

    @PostMapping("/add")
    public String addWishItem() {
        return "index";
    }

    @PostMapping("/wishlist/update/{id}")
    public String updateWishItem() {
        return "index";
    }

    @PostMapping("/wishlist/delete/{id}")
    public String removeItem(@PathVariable int id) {
        return "index";
    }
}
