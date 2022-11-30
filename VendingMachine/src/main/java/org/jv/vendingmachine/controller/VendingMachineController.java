package org.jv.vendingmachine.controller;

import org.jv.vendingmachine.dao.VendingMachinePersistenceException;
import org.jv.vendingmachine.dto.Row;
import org.jv.vendingmachine.service.InsufficientFundsException;
import org.jv.vendingmachine.service.NoItemInventoryException;
import org.jv.vendingmachine.service.VendingMachineService;
import org.jv.vendingmachine.view.InvalidVendingMachineSelection;
import org.jv.vendingmachine.view.VendingMachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class VendingMachineController {
    private VendingMachineService vendingMachineService;
    private VendingMachineView vendingMachineView;

    @Autowired
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
        Map<String, Row> rowItems = vendingMachineService.getRowItems();
        vendingMachineView.displayItemsInStock(rowItems);
    }
}
