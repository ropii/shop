package com.example.shop;

public class CreditCard {
    int cvc;
    int number;
    Date validation;

    public CreditCard(int cvc, int number, Date validation) {
        this.cvc = cvc;
        this.number = number;
        this.validation = validation;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getValidation() {
        return validation;
    }

    public void setValidation(Date validation) {
        this.validation = validation;
    }
}
