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
    public VendingMachineView(UserIOConsole userIOConsole) {
        this.userIOConsole = userIOConsole;
    }

    public void displayMenuBanner() {
        userIOConsole.print("=== Vend.io: The Ferrari of Vending Machines === \n  \t\t <=== Vending Machine Menu ===>");
    }

    public void displayErrorMessage(String err) {
        userIOConsole.print("=== Error ===");
        userIOConsole.print(err);
    }

    public int getMenuSelection() {
        displayMenuBanner();
        return userIOConsole.readInt("Please enter a menu selection: \n 1. Buy Product \n 2. Exit", 0, 2);
    }

    public void displayItemsInStock(Map<String, Row> rowItems) {
        List<String> rowNames = new ArrayList<>(rowItems.keySet());
        List<Row> rowWithNameAndItem = new ArrayList<>(rowItems.values());
        for(int i = 0; i < rowItems.size(); i++) {
            System.out.println("===== Row ID: " + rowNames.get(i) + " (Select Row ID + #) e.g \"" + rowNames.get(i) +  i +  "\" ====="); // Print current row letter identifier
            Row currentRow = rowWithNameAndItem.get(i);
            for(int j = 0; j < currentRow.getRowItems().size(); j++) {
                Item currentItem = currentRow.getRowItems().get(j);
                userIOConsole.print("\t\t ID: #" + j +  " " + currentItem);
            }
        }
    }

    public BigDecimal getUserDollarAmountAsBigDecimal() {
        return userIOConsole.readBigDecimal("Please input money in dollar amount $$: (e.g 2.50) ");
    }
    public void displayUserDollarAmount(BigDecimal amount) {
        userIOConsole.print("You entered: $" + amount.toString());
    }

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
