package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WishListController {

    @Autowired
    private WishListService wishListService;

    public WishListController() {}

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

    @GetMapping("/wishlist/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Wishitem wishItem = wishListService.getWishById(id);
        model.addAttribute("wishItem", wishItem);
        return "edit-wish"; // This will return the edit form page
    }

    @PostMapping("/wishlist/update/{id}")
    public String updateWishItem(@PathVariable int id, @ModelAttribute Wishitem wishItem) {
        // Update the wish item with new data
        wishListService.updateWish(id, wishItem);
        return "redirect:/wishlist"; // Redirect to the wishlist after updating
    }

    @PostMapping("/wishlist/delete/{id}")
    public String removeItem(@PathVariable int id) {
        // You can add logic here to remove an item
        wishListService.removeWish(id);
        return "redirect:/wishlist"; // Redirect to the wishlist after removal
    }
}
