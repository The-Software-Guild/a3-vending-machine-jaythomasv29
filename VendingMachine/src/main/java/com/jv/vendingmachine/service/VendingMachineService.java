package com.jv.vendingmachine.service;

import com.jv.vendingmachine.dao.VendingMachinePersistenceException;
import com.jv.vendingmachine.dto.Item;
import com.jv.vendingmachine.dto.Row;

import java.math.BigDecimal;
import java.util.Map;

public interface VendingMachineService {
    Map<String, Row> getItemsInStockDetails() throws VendingMachinePersistenceException;

    boolean checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws InsufficientFundsException;

//    void decreaseInventoryItemCount(String selection) throws NoItemInventoryException;

//    Item getItemInInventory(String selection, BigDecimal amount) throws NoItemInventoryException;

    void decreaseInventoryItemCount(String selection) throws NoItemInventoryException, VendingMachinePersistenceException;

    Map<String, Integer> processTransaction(BigDecimal customerMoney, String selection) throws NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException;

    Map<String, Integer> getChangePerCoin(BigDecimal changeTotal);

    Item getItemInInventory(String selection) throws NoItemInventoryException;
}
