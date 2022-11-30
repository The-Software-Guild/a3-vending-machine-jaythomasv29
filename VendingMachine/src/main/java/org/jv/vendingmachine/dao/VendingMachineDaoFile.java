package org.jv.vendingmachine.dao;

import org.jv.vendingmachine.dto.Item;
import org.jv.vendingmachine.dto.Row;

import java.util.List;
import java.util.Map;

public interface VendingMachineDaoFile {

    // Get All Items from vending machine?
    Map<String, Row> getAllRowsAsMap() throws VendingMachinePersistenceException;

    List<Item> getAllInventoryAsList() throws VendingMachinePersistenceException;

    Item getItemInInventory(char rowID, char colIdx);

    void writeInventory() throws VendingMachinePersistenceException;

    void loadRoster() throws VendingMachinePersistenceException;
}
