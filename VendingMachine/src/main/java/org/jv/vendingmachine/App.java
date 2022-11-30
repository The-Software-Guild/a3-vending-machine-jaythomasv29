package org.jv.vendingmachine;

import org.jv.vendingmachine.controller.VendingMachineController;
import org.jv.vendingmachine.dao.*;
import org.jv.vendingmachine.dao.VendingMachinePersistenceException;
import org.jv.vendingmachine.service.InsufficientFundsException;
import org.jv.vendingmachine.service.NoItemInventoryException;
import org.jv.vendingmachine.view.InvalidVendingMachineSelection;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * App - Main Entry Point
 */
public class App {
    public static void main(String[] args) throws InvalidVendingMachineSelection, NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException {
//    UserIOConsole userIOConsole = new UserIOConsoleImpl();
//    VendingMachineView vendingMachineView = new VendingMachineView(userIOConsole);
//    VendingMachineAuditDaoFile vendingMachineAuditDaoFile = new VendingMachineAuditDaoFileImpl();
//    VendingMachineDaoFile vendingMachineDaoFile = new VendingMachineDaoFileImpl();
//    VendingMachineService vendingMachineService = new VendingMachineServiceImpl(vendingMachineDaoFile, vendingMachineAuditDaoFile);
//    VendingMachineController vendingMachineController = new VendingMachineController(vendingMachineService, vendingMachineView);
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("org.jv.vendingmachine");
        appContext.refresh();
        VendingMachineController controller = appContext.getBean("vendingMachineController", VendingMachineController.class);
        controller.run();


    }

}
