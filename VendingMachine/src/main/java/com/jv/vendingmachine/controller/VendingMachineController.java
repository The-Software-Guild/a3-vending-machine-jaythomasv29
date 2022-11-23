package com.jv.vendingmachine.controller;

import com.jv.vendingmachine.dao.VendingMachineDaoFile;
import com.jv.vendingmachine.dao.VendingMachinePersistenceException;
import com.jv.vendingmachine.dto.Item;
import com.jv.vendingmachine.dto.Row;
import com.jv.vendingmachine.service.InsufficientFundsException;
import com.jv.vendingmachine.service.NoItemInventoryException;
import com.jv.vendingmachine.service.VendingMachineService;
import com.jv.vendingmachine.view.InvalidVendingMachineSelection;
import com.jv.vendingmachine.view.UserIOConsole;
import com.jv.vendingmachine.view.UserIOConsoleImpl;
import com.jv.vendingmachine.view.VendingMachineView;

import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineController {
    private VendingMachineService vendingMachineService;
    private VendingMachineView vendingMachineView;

    public VendingMachineController(VendingMachineService vendingMachineService, VendingMachineView vendingMachineView) {
        this.vendingMachineService = vendingMachineService;
        this.vendingMachineView = vendingMachineView;
    }

    public void run() throws InvalidVendingMachineSelection, NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException {
        boolean keepGoing = true;
        int menuSelection = 0;
            while(keepGoing) {
                vendingMachineView.displayMenuBanner();
                try {
                    this.getVendingDisplayMenu();
                } catch (VendingMachinePersistenceException e) {
                    vendingMachineView.displayErrorMessage("Error loading items to vending machine");
                }
        menuSelection = vendingMachineView.getMenuSelection();
                switch (menuSelection) {
                    case 1:
                        processVendingTransaction();
                        break;
                    case 2:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            vendingMachineView.displayGoodbyeMessage();
    }

    private void processVendingTransaction() throws InvalidVendingMachineSelection, NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException {
        String itemSelection = "";
        BigDecimal inputDollarAmount;
        inputDollarAmount = vendingMachineView.getUserDollarAmountAsBigDecimal();
        vendingMachineView.displayUserDollarAmount(inputDollarAmount);
        itemSelection = vendingMachineView.getVendingMachineRowAndColumnItemSelection();

        Map<String, Integer> userChange = vendingMachineService.processTransaction(inputDollarAmount, itemSelection);
        try {
            vendingMachineView.displayTransactionResults(vendingMachineService.getItemInInventory(itemSelection), userChange);

        } catch (IndexOutOfBoundsException | NullPointerException | NoItemInventoryException e) {
            vendingMachineView.displayErrorMessage("This item is not available, select another...");
        }

    }

    private void unknownCommand() {
        vendingMachineView.displayErrorMessage("Unknown Vending Command, Please Enter Valid Selection");
    }

    /**
     * Get Menu by calling upon Service Obj to get all Row Items in Dao Obj
     * rowItems: A HashMap<String, Row> - Basically a Row is like A, B, C, D and Row is ArrayList<Item> for column # int like 1, 2, 3
     */
    private void getVendingDisplayMenu() throws VendingMachinePersistenceException {
        Map<String, Row> rowItems = vendingMachineService.getItemsInStockDetails();
        vendingMachineView.displayItemsInStock(rowItems);
    }
}
