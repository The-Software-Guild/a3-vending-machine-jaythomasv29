package com.jv.vendingmachine.dao;

import com.jv.vendingmachine.dto.Item;
import com.jv.vendingmachine.dto.Row;

import java.io.*;
import java.util.*;

public class VendingMachineDaoFileImpl implements VendingMachineDaoFile {
    public final String INVENTORY_FILE;
    public static final String DELIMITER = "::";
    private Map<String, Row> vendingMachine = new HashMap<>();
    // Get Item from Inventory (String row, int col)

    public VendingMachineDaoFileImpl() throws VendingMachinePersistenceException {
        INVENTORY_FILE = "inventory_file.txt";
        this.loadRoster();
    }
    public VendingMachineDaoFileImpl(String txtFile) throws VendingMachinePersistenceException {
        INVENTORY_FILE = txtFile;
        this.loadRoster();
    }

    // Get All Items from vending machine?
    @Override
    public Map<String, Row> getAllRowsAsMap() throws VendingMachinePersistenceException {
        return vendingMachine;
    }

    @Override
    public List<Item> getAllInventoryAsList() throws VendingMachinePersistenceException {

        List<Item> inventory = new ArrayList<>();
        List<Row> rows =  new ArrayList<>(vendingMachine.values());
        for (Row row : rows) {
            List<Item> currentRowItems = row.getRowItems();
            for(Item item : currentRowItems) {
                inventory.add(item);
            }
        }
        return inventory;
    }

    /**
     *
     * @param rowID - the key in the hashmap to be accessed
     * @param colIdx - the String representation of index to be parsed as int
     * @return Item obj that is from a "2d" array
     */
    @Override
    public Item getItemInInventory(char rowID, char colIdx) {
        Row currentRow = vendingMachine.get(String.valueOf(rowID));
        List<Item> itemsInCurrentRow = new ArrayList<>(currentRow.getRowItems());
        return itemsInCurrentRow.get(Integer.parseInt(String.valueOf(colIdx)));
    }

    /**
     * Private Methods
     */
    @Override
    public void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save inventory data");
        }

        String itemAsText;
        List<Item> inventoryList = this.getAllInventoryAsList();

        for(Item item : inventoryList) {
            itemAsText = marshallItem(item);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }
    @Override
    public void loadRoster() throws VendingMachinePersistenceException {
        int asciiLetter = 65; // Initial Row Value
        List<Item> tempRowItemsList = new ArrayList<>();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("-_- Could not load the products! Out of order -_-");
        }
        String currentLine;
        Item currentItem;
        int rangeStart = 0;
        int rangeEnd = 4;
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();

            // Put the items into a temp list
            currentItem = unmarshallItem(currentLine);
            tempRowItemsList.add(currentItem);

            // when we reach a length of 3 in our tempRowItemsList
            if(rangeStart == rangeEnd) {
                Row currentRow = new Row(String.valueOf((char)asciiLetter));
                currentRow.setRowItems(new ArrayList<>(tempRowItemsList.subList(rangeStart - 4, rangeEnd)));
                vendingMachine.put(String.valueOf((char)asciiLetter), currentRow);
                rangeEnd+=3;
                asciiLetter++;
            }
            rangeStart++;
        }
        scanner.close();

    }

    private Item unmarshallItem(String itemAsText) {
        String[] itemTokens = itemAsText.split(DELIMITER);
        String itemName = itemTokens[0];
        String itemCost = itemTokens[1];
        String itemStock = itemTokens[2];
        return new Item(itemName, itemCost, itemStock);
    }

    private String marshallItem(Item item) {
        StringBuilder itemAsText = new StringBuilder("");
        itemAsText.append(item.getName() + DELIMITER);
        itemAsText.append(item.getCost() + DELIMITER);
        itemAsText.append(item.getInventory());
        return itemAsText.toString();
    }
}
