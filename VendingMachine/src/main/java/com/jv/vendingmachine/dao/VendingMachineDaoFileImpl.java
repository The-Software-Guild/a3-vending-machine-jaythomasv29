package com.jv.vendingmachine.dao;

import com.jv.vendingmachine.dto.Item;
import com.jv.vendingmachine.dto.Row;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

public class VendingMachineDaoFileImpl implements VendingMachineDaoFile {
    public final String INVENTORY_FILE;
    public static final String DELIMITER = "::";
    private Map<String, Row> vendingMachine = new HashMap<>();

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

    /**
     * A method to get all items within each Row object as an ArrayList
     * @return inventory
     * @throws VendingMachinePersistenceException
     */
    @Override
    public List<Item> getAllInventoryAsList() throws VendingMachinePersistenceException {
        List<Item> inventory = new ArrayList<>();
        List<Row> rows =  new ArrayList<>(vendingMachine.values());
        for (Row row : rows) {
            List<Item> currentRowItems = row.getRowItems();
//            for(Item item : currentRowItems) {
//                inventory.add(item);
//            }
            Consumer<Item> item = s -> { inventory.add(s); };
            currentRowItems.stream().forEach(item);
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
        List<Item> tempRowItemsList = new ArrayList<>();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("-_- Could not load the products! Out of order -_-");
        }
        String currentLine;
        Item currentItem;

        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();

            // Put the items into a temp list
            currentItem = unmarshallItem(currentLine);
            tempRowItemsList.add(currentItem);
        }
        scanner.close();
        // when we reach a length of 3 in our tempRowItemsList
      loadRowsIntoMap(tempRowItemsList);
    }

    private void loadRowsIntoMap(List<Item> tempRowItemsList) {
        int rangeEnd = 5;
        int asciiLetter = 65; // Initial Row Value
        for(int i = 0; i <= tempRowItemsList.size(); i++) {
            if(i == rangeEnd) {
                Row currentRow = new Row(String.valueOf((char)asciiLetter));
                currentRow.setRowItems(new ArrayList<>(tempRowItemsList.subList(i - 5, rangeEnd)));
                vendingMachine.put(String.valueOf((char)asciiLetter), currentRow);
                rangeEnd+=5;
                asciiLetter++;
            }
        }
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
