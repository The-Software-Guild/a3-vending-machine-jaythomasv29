package com.jv.vendingmachine.dao;

public interface VendingMachineAuditDaoFile {
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
