package com.example.shop;

import java.util.ArrayList;
import java.util.HashMap;

public class Buyer extends Person{

    CreditCard card;
    int zip;
    private ArrayList<Product> history;
    private ArrayList<Product> items;
    private int money=0;
    HashMap<Buyer, ArrayList<Product>> orders = new HashMap<Buyer, ArrayList<Product>>();


    public Buyer(String firstName, String lastName, String email, String password, CreditCard card, int zip) {
        super(firstName, lastName, email, password);
        this.card = card;
        this.zip = zip;
    }

    public Buyer(Person p, CreditCard card, int zip) {
        super(p.getFirstName(), p.getLastName(), p.getEmail(), p.getPassword());
        this.card = card;
        this.zip = zip;
    }

    public CreditCard getCard() {
        return card;
    }
    public void setCard(CreditCard card) {
        this.card = card;
    }
    public int getZip() {
        return zip;
    }
    public void setZip(int zip) {
        this.zip = zip;
    }
    public ArrayList<Product> getHistory() {
        return history;
    }
    public void setHistory(ArrayList<Product> history) {
        this.history = history;
    }




    public ArrayList<Product> getItems() {
        return items;
    }
    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public HashMap<Buyer, ArrayList<Product>> getOrders() {
        return orders;
    }
    public void setOrders(HashMap<Buyer, ArrayList<Product>> orders) {
        this.orders = orders;
    }
}

