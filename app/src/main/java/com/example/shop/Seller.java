package com.example.shop;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Buyer{

    private ArrayList<Product> items;
    private int money=0;
    HashMap<Buyer, ArrayList<Product>> orders = new HashMap<Buyer, ArrayList<Product>>();


    public Seller(String firstName, String lastName, String email, String password, CreditCard card, int zip) {
        super(firstName, lastName, email, password, card, zip);
    }
    public Seller(Buyer buyer) {
        super(buyer.getFirstName(), buyer.getLastName(), buyer.getEmail(), buyer.getPassword(), buyer.card, buyer.zip);
    }

}
