package org.example.wishlist6.Module;

public class Wishlist {
    private int wishListID;
    private String wishListName;

    public Wishlist(int wishListID, String wishListName) {
        this.wishListID = wishListID;
        this.wishListName = wishListName;
    }
    public Wishlist(){

    }

    public int getWishListID() {
        return wishListID;
    }

    public void setWishListID(int wishListID) {
        this.wishListID = wishListID;
    }

    public String getWishListName() {
        return wishListName;
    }

    public void setWishListName(String wishListName) {
        this.wishListName = wishListName;
    }
}
