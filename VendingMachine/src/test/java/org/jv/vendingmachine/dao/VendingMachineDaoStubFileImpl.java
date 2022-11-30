package org.jv.vendingmachine.dao;

import org.jv.vendingmachine.dto.Item;
import org.jv.vendingmachine.dto.Row;

import java.util.*;

public class VendingMachineDaoStubFileImpl implements VendingMachineDaoFile {
    public HashMap<String, Row> stubVendingMachineData = new HashMap<>();

    public VendingMachineDaoStubFileImpl() {
        // initialize Items to fill with dummy data
/**
 * Row 1 (4 Items per Row)
 */
        Item item1 = new Item ("Sprite", "1.50", 0);
        Item item2 = new Item ("Sprite", "1.50", 5);
        Item item3 = new Item ("Sprite", "1.50", 5);
        Item item4 = new Item ("Sprite", "1.50", 5);
        Row row1 = new Row("A", new ArrayList<>(Arrays.asList(item1, item2, item3, item4)));
/**
 * Row 2
 */
        Item item5 = new Item ("Sprite", "1.50", 5);
        Item item6 = new Item ("Sprite", "1.50", 5);
        Item item7 = new Item ("Sprite", "1.50", 5);
        Item item8 = new Item ("Sprite", "1.50", 5);
        Row row2 = new Row("A", new ArrayList<>(Arrays.asList(item5, item6, item7, item8)));

        stubVendingMachineData.put("A", row1);
        stubVendingMachineData.put("B", row2);
    }

    @Override
    public Map<String, Row> getAllRowsAsMap() throws VendingMachinePersistenceException {
    // This returns Rows
        return stubVendingMachineData;
    }

    @Override
    public List<Item> getAllInventoryAsList() throws VendingMachinePersistenceException {
        List<Item> allInventoryItemsList = new ArrayList<>();
        List<Row> rows =  new ArrayList<>(stubVendingMachineData.values());
        for (Row row : rows) {
            List<Item> currentRowItems = row.getRowItems();
            for(Item item : currentRowItems) {
                allInventoryItemsList.add(item);
            }
        }
        return allInventoryItemsList;
    }

    @Override
    public Item getItemInInventory(char rowID, char colIdx) {
        Row currentRow = stubVendingMachineData.get(String.valueOf(rowID));
        return currentRow.getRowItems().get(Integer.parseInt(String.valueOf(colIdx)));
    }

    @Override
    public void writeInventory() throws VendingMachinePersistenceException {

    }

    @Override
    public void loadRoster() throws VendingMachinePersistenceException {

    }

}
