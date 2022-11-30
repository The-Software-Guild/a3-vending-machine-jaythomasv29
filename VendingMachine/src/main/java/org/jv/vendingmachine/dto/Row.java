package org.jv.vendingmachine.dto;

import java.util.List;

/**
 * Row Model to house
 */
public class Row {
    private String name;
    private List<Item> rowItems;

    public Row(String name) {
        this.name = name;

    }

    public Row(String name, List<Item> rowItems) {
        this.name = name;
        this.rowItems = rowItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getRowItems() {
        return rowItems;
    }

    public void setRowItems(List<Item> rowItems) {
        this.rowItems = rowItems;
    }

    @Override
    public String toString() {
        return "Row{" +
                "name='" + name + '\'' +
                ", rowItems=" + rowItems.toString() +
                '}';
    }
}
