package com.jv.vendingmachine.dto;

public enum Coin {
    QUARTER(0.25), DIME(0.10), NICKEL(0.05), PENNY(0.01);
    private final double value;

    Coin (double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String toString(){
        switch (this) {
            case QUARTER:
                return "0.25 (Quarters)";
            case DIME:
                return "0.10 (Dimes)";
            case NICKEL:
                return "0.05 (Nickels)";
            case PENNY:
                return "0.01 (Pennys)";
        }
        return null;
    }
}
