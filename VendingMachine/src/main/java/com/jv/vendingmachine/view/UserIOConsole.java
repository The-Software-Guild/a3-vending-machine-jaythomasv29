package com.jv.vendingmachine.view;

import java.math.BigDecimal;

public interface UserIOConsole {
    void print(String msg);

    String readString(String msgPrompt);

    int readInt(String msgPrompt);

    int readInt(String msgPrompt, int min, int max);

    long readLong(String msgPrompt);

    long readLong(String msgPrompt, long min, long max);

    float readFloat(String msgPrompt);

    float readFloat(String msgPrompt, float min, float max);

    double readDouble(String msgPrompt);

    double readDouble(String msgPrompt, double min, double max);

    String readLetterAndNumberSequence(String msgPrompt) throws InvalidVendingMachineSelection;

    abstract BigDecimal readBigDecimal(String prompt);
}
