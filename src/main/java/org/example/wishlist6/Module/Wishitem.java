package org.example.wishlist6.Module;

public class Wishitem {
    private int wishItemId;
    private String wishItemName;
    private String wishItemDescription;
    private Double wishItemPrice; // <-- brug Wrapper for at kunne være null
    private String wishUrl; // <-- matcher kolonnen 'wish_url' i databasen

    public Wishitem(String wishItemName, String wishItemDescription, Double wishItemPrice, String wishUrl) {
        this.wishItemName = wishItemName;
        this.wishItemDescription = wishItemDescription;
        this.wishItemPrice = wishItemPrice;
        this.wishUrl = wishUrl;
    }

    public Wishitem() {}

    public int getWishItemId() {
        return wishItemId;
    }

    public void setWishItemId(int wishItemId) {
        this.wishItemId = wishItemId;
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

    public Double getWishItemPrice() {
        return wishItemPrice;
    }

    public void setWishItemPrice(Double wishItemPrice) {
        this.wishItemPrice = wishItemPrice;
    }

    public String getWishUrl() {
        return wishUrl;
    }

    public void setWishUrl(String wishUrl) {
        this.wishUrl = wishUrl;
    }
}
