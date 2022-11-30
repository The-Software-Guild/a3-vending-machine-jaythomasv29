package org.jv.vendingmachine.dao;

import org.jv.vendingmachine.dto.Item;
import org.jv.vendingmachine.dto.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineDaoFileImplTest {
    VendingMachineDaoFile testVendingMachineDao;

    public VendingMachineDaoFileImplTest() {}


    @BeforeEach
    public void setUp() throws VendingMachinePersistenceException {
        testVendingMachineDao = new VendingMachineDaoFileImpl("inventory_file_test.txt");

    }


    @Test
    public void getAllItemsAsMap() throws VendingMachinePersistenceException {

        Map<String, Row> allItemsMap = testVendingMachineDao.getAllRowsAsMap();
        // test that all lines of data in inventory_file_test has loaded property, and that size of values() match
        List<Row> totalRows = new ArrayList<>(allItemsMap.values());
        assertNotNull(totalRows, "The list of all the Row objects must not be null");
        assertEquals(4, totalRows.size(), "The size of List<Row> must be 4");
    }

    @Test
    void getAllInventoryAsList() throws VendingMachinePersistenceException {

        List<Item> allInventoryItemsAsList = testVendingMachineDao.getAllInventoryAsList();
        assertNotNull(allInventoryItemsAsList, "The list of all Item objects must not be null");
        assertEquals(16, allInventoryItemsAsList.size(), "The size of List<Item> must be 16");
    }

    @Test
    void getItemInInventory() {
        char rowID = "A".charAt(0);
        char colID = "0".charAt(0);
        Item testItem = new Item("Sprite", "1.50", 1);
        Item foundItem = testVendingMachineDao.getItemInInventory(rowID, colID);
        assertEquals(testItem, foundItem);

    }

}