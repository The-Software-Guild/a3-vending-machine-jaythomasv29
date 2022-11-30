package org.jv.vendingmachine.view;

public class InvalidVendingMachineSelection extends Exception{
    public InvalidVendingMachineSelection(String message) {
        System.out.println(message);
    }

    public InvalidVendingMachineSelection(String message, Throwable cause) {
        super(message, cause);
    }
}
