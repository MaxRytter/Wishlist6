package org.example.wishlist6.Module;

public class Wishlist {
    private int wishListID;
    private String wishListName;

    public Wishlist(String wishListName) {
        this.wishListName = wishListName;
    }
    public Wishlist(){

    }

    public int getWishListID() {
        return wishListID;
    }

    public String getWishListName() {
        return wishListName;
    }

    public void setWishListName(String wishListName) {
        this.wishListName = wishListName;
    }
}
