package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("/")
    public String showFrontPage() {
        return "index";
    }

    @GetMapping("/wishlist")
    public String getWishlist(Model model) {
        List<Wishlist> wishlists = wishListService.getAllWishlists();
        model.addAttribute("wishlists", wishlists);
        return "wishlist";
    }


    @GetMapping("/wishlist/create")
    public String showCreateForm(Model model) {
        model.addAttribute("wishlist", new Wishlist());
        return "add-wishlist";
    }

    @PostMapping("/wishlist/create")
    public String addWishlist(@ModelAttribute Wishlist wishlist) {
        wishListService.addWishlist(wishlist);
        return "redirect:/wishlist";
    }

    @GetMapping("/wishlist/{id}/add-wish")
    public String showAddWishForm(@PathVariable("id") int wishlistId, Model model) {
        model.addAttribute("wishlistId", wishlistId);
        model.addAttribute("wish", new Wishitem());
        return "add-wish";
    }

    @PostMapping("/wishlist/{id}/add-wish")
    public String addWishToWishlist(@PathVariable("id") int wishlistId, @ModelAttribute Wishitem wish) {
        wishListService.saveWishToWishlist(wishlistId, wish);
        return "redirect:/wishlist";
    }


//    @GetMapping("/wishlist/{id}")
//    public String getWishlistByID() {
//        return "index";
//    }
//
//    @GetMapping("/wishlist/edit/{id}")
//    public String showEditForm(@PathVariable int id, Model model) {
//        Wishitem wishItem = wishListService.getWishById(id);
//        model.addAttribute("wishItem", wishItem);
//        return "edit-wish"; // This will return the edit form page
//    }
//
//    @PostMapping("/wishlist/update/{id}")
//    public String updateWishItem(@PathVariable int id, @ModelAttribute Wishitem wishItem) {
//        // Update the wish item with new data
//        wishListService.updateWish(id, wishItem);
//        return "redirect:/wishlist"; // Redirect to the wishlist after updating
//    }
//
//    @PostMapping("/wishlist/delete/{id}")
//    public String removeItem(@PathVariable int id) {
//        // You can add logic here to remove an item
//        wishListService.removeWish(id);
//        return "redirect:/wishlist"; // Redirect to the wishlist after removal
//    }
}
