package com.jv.vendingmachine.view;

import com.jv.vendingmachine.dao.VendingMachinePersistenceException;
import com.jv.vendingmachine.dto.Item;
import com.jv.vendingmachine.dto.Row;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendingMachineView {
    private UserIOConsole userIOConsole;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";



    public VendingMachineView(UserIOConsole userIOConsole) {
        this.userIOConsole = userIOConsole;
    }

    /**
     * A generic banner to display prior to menu selection
     */
    public void displayMenuBanner() {
        userIOConsole.print("===== Vend.io: The Ferrari of Vending Machines ===== \n  \t\t ----- Vending Machine Menu -----");
    }

    /**
     * A error message handling view to prompt the user
     * @param err
     */
    public void displayErrorMessage(String err) {
        userIOConsole.print("=== Error ===");
        userIOConsole.print(err);
    }

    /**
     * A method to get user input to
     * @return
     */
    public int getMenuSelection() {
        displayMenuBanner();
        return userIOConsole.readInt("Please enter a menu selection: \n 1. Buy Product \n 2. Exit", 0, 2);
    }

    /**
     * A nested loop to display ArrayList<Item> field that is inside Row using a nested loop O(n^2) runtime
     * @param rowItems
     */
    public void displayItemsInStock(Map<String, Row> rowItems) {
        List<String> rowNames = new ArrayList<>(rowItems.keySet());
        List<Row> rowWithNameAndItem = new ArrayList<>(rowItems.values());
        for(int i = 0; i < rowItems.size(); i++) {
            userIOConsole.print(ANSI_WHITE + "===== Row ID: " + rowNames.get(i) + " (Select Row ID + #) e.g \"" + rowNames.get(i) +  i +  "\" ====="); // Print current row letter identifier
            Row currentRow = rowWithNameAndItem.get(i);
            for(int j = 0; j < currentRow.getRowItems().size(); j++) {
                Item currentItem = currentRow.getRowItems().get(j);
                if(currentItem.getIsAvailable()) {
                userIOConsole.print(ANSI_GREEN+ "||\t\t#" + j +"\t" +   " " + currentItem + "\t\t\t||");
                } else {
                    userIOConsole.print(ANSI_RED+ "||\t\t#" + j +"\t" +   " " + currentItem + "\t\t\t||");
                }
            }
        }
    }

    /**
     * A big decimal input reader to get an amount of money in dollar form and is used for item selection
     * @return
     */
    public BigDecimal getUserDollarAmountAsBigDecimal() {
        return userIOConsole.readBigDecimal("Please input money in dollar amount $$: (e.g 2.50) ");
    }

    /**
     * Generic banner to display dollar amount that user entered
     * @param amount
     */
    public void displayUserDollarAmount(BigDecimal amount) {
        userIOConsole.print("You entered: $" + amount.toString());
    }

    /**
     * A method to read input from user to get a letter and a digit, the user MUST enter a valid format or an exception will be thrown
     * Example of valid input "a0, B2, etc."
     * Example of invalid input "a, aA, 0A, 00 "(empty string)" "
     * @return
     * @throws InvalidVendingMachineSelection
     */
    public String getVendingMachineRowAndColumnItemSelection() throws InvalidVendingMachineSelection {
        return userIOConsole.readLetterAndNumberSequence(" Please select an item from the menu above: ");
    }

    public void displayGoodbyeMessage() {
        userIOConsole.print("Thank you, and Good bye");
    }

    public void nextStepMessage() {
        userIOConsole.readString("Press enter to continue...");
    }

    public void displayTransactionResults(Item itemInInventory, Map<String, Integer> userChange) {
        userIOConsole.print("You have successfully bought a " + itemInInventory.getName() + " for " + itemInInventory.getCost() + " and will receive change as followed: \n " + userChange.toString());
        nextStepMessage();
    }

}
