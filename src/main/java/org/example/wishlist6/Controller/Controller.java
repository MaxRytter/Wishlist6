package org.example.wishlist6.Controller;

import jakarta.servlet.annotation.HandlesTypes;
import org.example.wishlist6.Module.Wishitem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
//Alt nedenunder indtil videre er et "skellet" taget fra 2. del af touristguide.
//Muligvis er der andre klasser eller en anden logik der skal tilføjes
//Logikken kan være meget anderledes pga. SQL osv. så ikke vær bange for at slette ting nedenunder der ikke giver mening
//
public class Controller {

    public Controller() {
    }
@GetMapping("/")
    public String showFrontPage() {
    return "index";
}
@GetMapping("/wishlist")
    public String getWishlist() {
    return "index";
    }
@GetMapping("/wishlist/{id}")
    public String getWishlistByID() {
        return "index";
    }
@PostMapping("/add")
public ResponseEntity<Wishitem> addWishItem () {
    Wishitem newItem = Service.addItem(wishitem);
    return new ResponseEntity<>(newItem, HttpStatus.CREATED);
}
@PostMapping("/wishlist/update/{id]}")
    public ResponseEntity<Wishitem> updateWishItem () {
    Wishitem updateItem = Service.addItem(wishitem);
        return new ResponseEntity<>(updateItem, HttpStatus.OK);
    }
@PostMapping("/wishlist/delete/{id}")
public ResponseEntity<Wishitem> removeItem(@PathVariable int id) {
    Wishitem deleteItem = Service.removeItem(id);
    return new ResponseEntity<>(deleteItem, HttpStatus.OK);
}


}

