package com.example.shop;


// אוביקט מסוג מוצר חייב להכיל: שם, קטגוריה, תמונה( כתתובת של תמנונה לאחסון בענן), מחיר ותג
// לאוביקט מסוג זה יש אופציה להכיל תיאור( לא חובה להיות בעל תיאור)
public class Product {
    private String name;
    private String category;
    private String description;
    private String imgUrl;
    private String productId;
    private int price;

    public Product(String name, String category, String imgId, int price,String productId) {
        this.name = name;
        this.category = category;
        this.imgUrl = imgId;
        this.price = price;
        this.productId = productId;
    }
    public Product(String name,String category, String  imgId, int price,String productId,String description) {
        this.name = name;
        this.category = category;
        this.imgUrl = imgId;
        this.price = price;
        this.productId = productId;
        this.description = description;
    }
    public Product(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
