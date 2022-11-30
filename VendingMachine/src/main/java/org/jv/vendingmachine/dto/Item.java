package org.jv.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Item Model
 *
 */
public class Item {

    private String name;
    private BigDecimal cost;
    private int inventory;
    private boolean isAvailable;



    public Item(String name, String cost, int inventory) {
        this.name = name;
        this.cost = new BigDecimal(cost);
        this.inventory = inventory;
        this.isAvailable = this.inventory > 0;
    }

    public Item(String name, String cost, String inventory) {
        this.name = name;
        this.cost = new BigDecimal(cost);
        this.inventory = Integer.parseInt(inventory);
        this.isAvailable = this.inventory > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public boolean getIsAvailable() { return this.isAvailable; };

    public void setIsAvailable() {
        this.isAvailable = !this.isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return inventory == item.inventory && isAvailable == item.isAvailable && name.equals(item.name) && cost.equals(item.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, inventory, isAvailable);
    }

    @Override
    public String toString() {
        return "[" +
                name +
                ": $" + cost +
                ", stock: " + inventory +
                ']';
    }


}
