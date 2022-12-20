package com.example.shop;

public class Product {
    private String name;
    private String category;
    private int rate;
    private int imgId;
    private int price;

    public Product(String name,String category, int imgId, int price) {
        this.name = name;
        this.category = category;
        this.imgId = imgId;
        this.price = price;
    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public int getRate() {
        return rate;
    }
    public int getImgId() {
        return imgId;
    }
    public int getPrice() {
        return price;
    }

    public int getImageId() {
        return imgId;
    }
}
