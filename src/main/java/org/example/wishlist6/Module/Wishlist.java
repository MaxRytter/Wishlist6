package org.example.wishlist6.Module;

public class Wishlist {
    private int wishListID;
    private String wishListName;

    public Wishlist(String wishListName, int wishListID) {
        this.wishListName = wishListName;
        this.wishListID = wishListID;
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
