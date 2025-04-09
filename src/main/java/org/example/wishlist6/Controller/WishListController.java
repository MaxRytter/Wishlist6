package org.example.wishlist6.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String getAllWishlists(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        List<Wishlist> wishlists = wishListService.getWishlistsByUserId(userId);

        model.addAttribute("wishlists", wishlists);
        return "wishlist";
    }


    @GetMapping("/wishlist/create")
    public String showCreateForm(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        model.addAttribute("wishlist", new Wishlist());
        return "add-wishlist";
    }

    @PostMapping("/wishlist/create")
    public String addWishlist(@RequestParam String wishListName, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        System.out.println("User ID: " + userId);

        Wishlist wishlist = new Wishlist();
        wishlist.setWishListName(wishListName);
        wishlist.setUserId(userId);

        System.out.println("Created Wishlist: " + wishlist.getWishListName() + " for User ID: " + wishlist.getUserId());

        wishListService.saveWishlist(wishlist);
        return "redirect:/wishlist";
    }

    @GetMapping("/wishlist/{id}")
    public String viewWishlist(@PathVariable("id") int wishlistId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        Wishlist wishlist = wishListService.getWishlistById(wishlistId); // includes name etc.
        List<Wishitem> wishes = wishListService.getWishesByWishlistId(wishlistId); // all wishes

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("wishes", wishes);
        return "view-wishlist";
    }

    @GetMapping("/wishlist/{id}/add-wish")
    public String showAddWishForm(@PathVariable("id") int wishlistId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        model.addAttribute("wishlistId", wishlistId);
        model.addAttribute("wish", new Wishitem());
        return "add-wish";
    }

    @PostMapping("/wishlist/{id}/add-wish")
    public String addWishToWishlist(@PathVariable("id") int wishlistId, @ModelAttribute Wishitem wish) {
        wishListService.saveWishToWishlist(wishlistId, wish);
        return "redirect:/wishlist/{id}";
    }
    @PostMapping("/wishlist/delete/{id}")
    public String deleteWishlist(@PathVariable int id) {
        wishListService.deleteWishlistById(id);
        return "redirect:/wishlist";
    }
    @GetMapping("/wishlist/delete/confirm/{id}")
    public String confirmDeleteWishlist(@PathVariable int id, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        Wishlist wishlist = wishListService.getWishlistById(id);
        model.addAttribute("wishlistId", id);
        model.addAttribute("wishlist", wishlist);
        return "confirm-delete-wishlist";
    }

    @GetMapping("/wishlist/{wishlistId}/delete-wish/{wishId}")
    public String deleteWish(@PathVariable("wishlistId") int wishlistId, @PathVariable("wishId") int wishId) {
        wishListService.deleteWishById(wishId);
        return "redirect:/wishlist/" + wishlistId;
    }
    @GetMapping("/wishlist/{wishlistId}/edit-wish/{wishId}")
    public String showEditWishForm(@PathVariable("wishlistId") int wishlistId, @PathVariable("wishId") int wishId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        Wishitem wish = wishListService.getWishById(wishId);
        model.addAttribute("wish", wish);
        model.addAttribute("wishlistId", wishlistId);
        return "edit-wish";
    }


    @PostMapping("/wishlist/{wishlistId}/edit-wish/{wishId}")
    public String editWish(@PathVariable("wishlistId") int wishlistId, @PathVariable("wishId") int wishId, @ModelAttribute Wishitem wish) {
        wish.setWishItemId(wishId);
        wishListService.updateWishItem(wish);
        return "redirect:/wishlist/" + wishlistId;
    }
    @GetMapping("/wishlist/{id}/edit")
    public String showEditWishlistForm(@PathVariable("id") int wishlistId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        Wishlist wishlist = wishListService.getWishlistById(wishlistId);
        model.addAttribute("wishlistId", wishlistId);
        model.addAttribute("wishlist", wishlist);
        return "edit-wishlist";
    }
    @PostMapping("/wishlist/{id}/edit")
    public String editWishlist(@PathVariable("id") int wishlistId, @ModelAttribute Wishlist wishlist) {
        wishlist.setWishListID(wishlistId);
        wishListService.updateWishlist(wishlist);
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

    @GetMapping("/test/403")
    public String test403() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden!");
    }

    @GetMapping("/test/404")
    public String test404() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!");
    }

    @GetMapping("/test/408")
    public String test408() {
        throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, "Timeout!");
    }

    @GetMapping("/test/500")
    public String test500() {
        throw new RuntimeException("Boom 💥");
    }
}
