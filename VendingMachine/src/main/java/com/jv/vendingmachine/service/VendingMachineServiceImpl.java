package com.jv.vendingmachine.service;

import com.jv.vendingmachine.dao.VendingMachineAuditDaoFile;
import com.jv.vendingmachine.dao.VendingMachineDaoFile;
import com.jv.vendingmachine.dao.VendingMachinePersistenceException;
import com.jv.vendingmachine.dto.Coin;
import com.jv.vendingmachine.dto.Item;
import com.jv.vendingmachine.dto.Row;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class VendingMachineServiceImpl implements VendingMachineService{
    private VendingMachineDaoFile vendingMachineDao;
    private VendingMachineAuditDaoFile vendingMachineAuditDao;

    public VendingMachineServiceImpl(VendingMachineDaoFile vendingMachineDao, VendingMachineAuditDaoFile vendingMachineAuditDao) {
        this.vendingMachineDao = vendingMachineDao;
        this.vendingMachineAuditDao = vendingMachineAuditDao;
    }

    @Override
    public Map<String, Row> getItemsInStockDetails() throws VendingMachinePersistenceException {
        Map<String, Row> rowItems = vendingMachineDao.getAllRowsAsMap();
        return rowItems;
    }

    @Override
    public boolean checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws InsufficientFundsException {
        //Checks if the user has input enough money to buy selected item
        //If the cost of the item is greater than the amount of money put in
        if (item.getCost().compareTo(inputMoney)==1) {
            throw new InsufficientFundsException (
                    "ERROR: insufficient funds, you have only input "+ inputMoney);
        }
        return true;
    }
    @Override
    public void decreaseInventoryItemCount(String selection) throws NoItemInventoryException {
        Item foundProduct = vendingMachineDao.getItemInInventory(selection.charAt(0), selection.charAt(1));
        // If product field isAvailable is false, -> throw exception
        if(!foundProduct.getIsAvailable()) throw new NoItemInventoryException("Item not available");
        // Otherwise: decrease foundProduct inventory by 1
        foundProduct.setInventory(foundProduct.getInventory() - 1);
        // Check if foundProduct's inventory has been decreased to 0, -> set it as "unavailable"
        if(foundProduct.getInventory() == 0) {
            foundProduct.setIsAvailable();
        }
    }

    /**
     * Processes transaction and manages inventory in database
     * @param customerMoney
     * @param selection
     * @return Change from the transaction of the "dispensed" item
     * @throws NoItemInventoryException
     * @throws InsufficientFundsException
     */
    @Override
    public  Map<String, Integer> processTransaction(BigDecimal customerMoney, String selection) throws NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException {
        Item foundItem;
        try {
            foundItem = getItemInInventory(selection);
            checkIfEnoughMoney(foundItem, customerMoney);
        }catch(NoItemInventoryException | IndexOutOfBoundsException | InsufficientFundsException e ) {
            System.out.println("Error, occurred when processing transaction...Try again");
            return null;
            }
        decreaseInventoryItemCount(selection);
        BigDecimal totalChange = customerMoney.subtract(foundItem.getCost());
        Map<String, Integer> changeResults = getChangePerCoin(totalChange);
        vendingMachineDao.writeInventory();
        return changeResults;

    }
    @Override
    public Map<String, Integer> getChangePerCoin(BigDecimal changeTotal) {
        Map<String, Integer> changePerCoin = new HashMap<>();
        double total = changeTotal.doubleValue();
        while (total > 0) {
            if(total >= Coin.QUARTER.getValue()) {
                if(changePerCoin.get(Coin.QUARTER.toString()) == null) {
                    changePerCoin.put(Coin.QUARTER.toString(), 1);
                } else {
                    changePerCoin.put(Coin.QUARTER.toString(), changePerCoin.get(Coin.QUARTER.toString()) + 1);
                }
                total -= Coin.QUARTER.getValue();
            } else if (total >= Coin.DIME.getValue()) {
                if(changePerCoin.get(Coin.DIME.toString()) == null) {
                    changePerCoin.put(Coin.DIME.toString(), 1);
                } else {
                    changePerCoin.put(Coin.DIME.toString(), changePerCoin.get(Coin.DIME.toString()) + 1);
                }
                total -= Coin.DIME.getValue();
            } else if (total >= Coin.NICKEL.getValue()) {
                if(changePerCoin.get(Coin.NICKEL.toString()) == null) {
                    changePerCoin.put(Coin.NICKEL.toString(), 1);
                } else {
                    changePerCoin.put(Coin.NICKEL.toString(), changePerCoin.get(Coin.NICKEL.toString()) + 1);
                }
                total -= Coin.NICKEL.getValue();
            } else {
                if(changePerCoin.get(Coin.PENNY.toString()) == null) {
                    changePerCoin.put(Coin.PENNY.toString(), 1);
                } else {
                    changePerCoin.put(Coin.PENNY.toString(), changePerCoin.get(Coin.PENNY.toString()) + 1);
                }
                total -= Coin.PENNY.getValue();
            }
        }
        return changePerCoin;
    }

    /**
     * Retrieves the Item within the Map by utilizing selection sequence
     * - e.g. A2 specifies key "A" in hashmap and 2 specifies the array index in the Row object
     * @param selection
     * @return
     * @throws NoItemInventoryException
     */
    @Override
    public Item getItemInInventory(String selection) throws NoItemInventoryException {
        Item product = vendingMachineDao.getItemInInventory(selection.charAt(0), selection.charAt(1));
        // if product does not exist throw NoItemInventoryException
        if(product == null || !product.getIsAvailable()) throw new NoItemInventoryException("Item at location " + selection + " is not available");
        return product;
    }

}
