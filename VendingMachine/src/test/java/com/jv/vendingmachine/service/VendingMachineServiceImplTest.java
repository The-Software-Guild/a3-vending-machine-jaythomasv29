package com.jv.vendingmachine.service;

import com.jv.vendingmachine.dao.VendingMachineAuditDaoFile;
import com.jv.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.jv.vendingmachine.dao.VendingMachineDaoFile;
import com.jv.vendingmachine.dao.VendingMachinePersistenceException;
import com.jv.vendingmachine.dto.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceImplTest {
    private VendingMachineService vendingMachineService;

    public VendingMachineServiceImplTest() {
        VendingMachineDaoFile vendingMachineDaoFile = new VendingMachineDaoStubFileImpl();
        VendingMachineAuditDaoFile vendingMachineAuditDaoFile = new VendingMachineAuditDaoFileImpl();
        vendingMachineService = new VendingMachineServiceImpl(vendingMachineDaoFile, vendingMachineAuditDaoFile);
    }

    @Test
    public void getItemsInStockDetails() {
        try {
            vendingMachineService.getItemsInStockDetails();
        } catch (VendingMachinePersistenceException e) {
            fail("Couldn't get Rows with item in stock");
        }
    }

    @Test
    void checkIfEnoughMoney() {
        try {
            vendingMachineService.checkIfEnoughMoney(new Item("Pringles", "2.00", 5), new BigDecimal("2.50"));
        } catch (InsufficientFundsException e) {
            fail("Exception should NOT be thrown, User has enough money");
        }

        try {
            vendingMachineService.checkIfEnoughMoney(new Item("Pringles", "2.00", 5), new BigDecimal("1.50"));
            fail("Exception should be thrown, User does not have enough money");
        } catch (InsufficientFundsException e) {
        }
    }

    @Test
    void decreaseInventoryItemCount() {
        try {
            vendingMachineService.decreaseInventoryItemCount("A0");
            fail("Exception should be thrown, A0 has inventory of 0");
        } catch (NoItemInventoryException e) {

        }
    }

    @Test
    void processTransaction() {
        try {
            vendingMachineService.processTransaction(new BigDecimal("2.50"), "A0");
        } catch(NoItemInventoryException | IndexOutOfBoundsException | InsufficientFundsException |
                VendingMachinePersistenceException e ) {
            fail("Exception SHOULD NOT be thrown, catch would not seek thrown error");
        }

        try {
            vendingMachineService.processTransaction(new BigDecimal("2.50"), "A9");
        } catch(NoItemInventoryException | IndexOutOfBoundsException | InsufficientFundsException |
                VendingMachinePersistenceException e ) {
            fail("Exception SHOULD NOT be thrown, but error message prints to notify user");
        }
    }

    @Test
    void getChangePerCoin() {
    }

    @Test
    void getItemInInventory() {
        try {
            vendingMachineService.getItemInInventory("A2");
        } catch (NoItemInventoryException e) {
            fail("Exception SHOULD NOT be thrown, upon successful inventory retrieval from DAO");
        }

        try {
            // INVALID INPUT TEST
            vendingMachineService.getItemInInventory("A9");
            fail("Exception SHOULD NOT be thrown, but User is prompted with error message");
        } catch (NoItemInventoryException | IndexOutOfBoundsException e) {
        }
    }
}