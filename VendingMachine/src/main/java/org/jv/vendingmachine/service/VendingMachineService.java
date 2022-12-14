package org.jv.vendingmachine.service;

import org.jv.vendingmachine.dao.VendingMachinePersistenceException;
import org.jv.vendingmachine.dto.Item;
import org.jv.vendingmachine.dto.Row;

import java.math.BigDecimal;
import java.util.Map;

public interface VendingMachineService {
    Map<String, Row> getRowItems() throws VendingMachinePersistenceException;

    boolean checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws InsufficientFundsException;

//    void decreaseInventoryItemCount(String selection) throws NoItemInventoryException;

//    Item getItemInInventory(String selection, BigDecimal amount) throws NoItemInventoryException;

    void decreaseInventoryItemCount(String selection) throws NoItemInventoryException, VendingMachinePersistenceException;

    Map<String, Integer> processTransaction(BigDecimal customerMoney, String selection) throws NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException;

    Map<String, Integer> getChangePerCoin(BigDecimal changeTotal);

    Item getItemInInventory(String selection) throws NoItemInventoryException;
}
