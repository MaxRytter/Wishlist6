package org.example.wishlist6.Module;

public class Wishitem {
    private int wishItemId;
    private String wishItemName;
    private String wishItemDescription;
    private double wishItemPrice;
    private String linkToStore;

    public Wishitem(String wishItemName, String wishItemDescription, double wishItemPrice, String linkToStore) {
        this.wishItemName = wishItemName;
        this.wishItemDescription = wishItemDescription;
        this.wishItemPrice = wishItemPrice;
        this.linkToStore = linkToStore;
    }
    public Wishitem(){

    }

    public int getWishItemID() {
        return wishItemId;
    }

    public String getWishItemName() {
        return wishItemName;
    }

    public void setWishItemName(String wishItemName) {
        this.wishItemName = wishItemName;
    }

    public String getWishItemDescription() {
        return wishItemDescription;
    }

    public void setWishItemDescription(String wishItemDescription) {
        this.wishItemDescription = wishItemDescription;
    }

    public double getWishItemPrice() {
        return wishItemPrice;
    }

    public void setWishItemPrice(double wishItemPrice) {
        this.wishItemPrice = wishItemPrice;
    }

    public String getLinkToStore() {
        return linkToStore;
    }

    public void setLinkToStore(String linkToStore) {
        this.linkToStore = linkToStore;
    }
}
