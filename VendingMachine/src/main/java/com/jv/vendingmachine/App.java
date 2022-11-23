package com.jv.vendingmachine;

import com.jv.vendingmachine.controller.VendingMachineController;
import com.jv.vendingmachine.dao.*;
import com.jv.vendingmachine.service.InsufficientFundsException;
import com.jv.vendingmachine.service.NoItemInventoryException;
import com.jv.vendingmachine.service.VendingMachineService;
import com.jv.vendingmachine.service.VendingMachineServiceImpl;
import com.jv.vendingmachine.view.InvalidVendingMachineSelection;
import com.jv.vendingmachine.view.UserIOConsole;
import com.jv.vendingmachine.view.UserIOConsoleImpl;
import com.jv.vendingmachine.view.VendingMachineView;

/**
 * App - Main Entry Point
 */
public class App {
    public static void main(String[] args) throws InvalidVendingMachineSelection, NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException {
    UserIOConsole userIOConsole = new UserIOConsoleImpl();
    VendingMachineView vendingMachineView = new VendingMachineView(userIOConsole);
    VendingMachineAuditDaoFile vendingMachineAuditDaoFile = new VendingMachineAuditDaoFileImpl();
    VendingMachineDaoFile vendingMachineDaoFile = new VendingMachineDaoFileImpl();
    VendingMachineService vendingMachineService = new VendingMachineServiceImpl(vendingMachineDaoFile, vendingMachineAuditDaoFile);
    VendingMachineController vendingMachineController = new VendingMachineController(vendingMachineService, vendingMachineView);
    vendingMachineController.run();


    }

}
