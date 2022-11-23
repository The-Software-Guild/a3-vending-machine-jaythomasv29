package com.jv.vendingmachine.view;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIOConsole {
        static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_WHITE = "\u001B[37m";

    final private Scanner console = new Scanner(System.in);

        /**
         *
         * A very simple method that takes in a message to display on the console
         * and then waits for a integer answer from the user to return.
         *
         * @param msg - String of information to display to the user.
         *
         */
        @Override
        public void print(String msg) {
            System.out.println(ANSI_WHITE+ ANSI_BLACK_BACKGROUND + msg);
        }

        /**
         *
         * A simple method that takes in a message to display on the console,
         * and then waits for an answer from the user to return.
         *
         * @param msgPrompt - String explaining what information you want from the user.
         * @return the answer to the message as string
         */
        @Override
        public String readString(String msgPrompt) {
            System.out.println(ANSI_BLACK_BACKGROUND + ANSI_WHITE +  msgPrompt);
            return console.nextLine();
        }

        /**
         *
         * A simple method that takes in a message to display on the console,
         * and continually reprompts the user with that message until they enter an integer
         * to be returned as the answer to that message.
         *
         * @param msgPrompt - String explaining what information you want from the user.
         * @return the answer to the message as integer
         */
        @Override
        public int readInt(String msgPrompt) {
            boolean invalidInput = true;
            int num = 0;
            while (invalidInput) {
                try {
                    // print the message msgPrompt (ex: asking for the # of cats!)
                    String stringValue = this.readString(msgPrompt);
                    // Get the input line, and try and parse
                    num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                    invalidInput = false; // or you can use 'break;'
                } catch (NumberFormatException e) {
                    // If it explodes, it'll go here and do this.
                    this.print("Input error. Please try again.");
                }
            }
            return num;
        }

    /**
     * Parse user input and ensure that it is the correct format, e.g a2, b3, C2
     *
        @param msgPrompt - String explaining what information you want from the user.
      * @return the answer to the message as String that is length: 2 & strictly a letter followed by number
     */
    @Override
        public String readLetterAndNumberSequence(String msgPrompt) throws InvalidVendingMachineSelection {
            boolean invalidInput = true;
            String selection = "";
            while(invalidInput) {
                selection = this.readString(msgPrompt);
                String[] selectionSeq = selection.split("");
                // check if the length of the array is strictly 2
                try {
                // check if 1st element is a valid letter with Regex
                // check if 2nd element is a valid number
                if (!selectionSeq[0].matches("[a-zA-Z]") || selectionSeq[1].matches("[a-zA-Z]") || selectionSeq.length != 2) throw new InvalidVendingMachineSelection("Invalid Input");
                invalidInput = false;
                } catch (InvalidVendingMachineSelection | ArrayIndexOutOfBoundsException| NumberFormatException e) {
                    print("Invalid Vending Machine Selection");
                }

                }
            print("You selected: " + selection.toUpperCase());
            return selection.toUpperCase();
        }

    @Override
    public BigDecimal readBigDecimal(String msgPrompt) {
        boolean invalidInput = true;
        double num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(msgPrompt);
                // Get the input line, and try and parse
                num = Double.parseDouble(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                this.print("Input error. Please try again.");
            }
        }
        return new BigDecimal(num);
    }

        /**
         *
         * A slightly more complex method that takes in a message to display on the console,
         * and continually reprompts the user with that message until they enter an integer
         * within the specified min/max range to be returned as the answer to that message.
         *
         * @param msgPrompt - String explaining what information you want from the user.
         * @param min - minimum acceptable value for return
         * @param max - maximum acceptable value for return
         * @return an integer value as an answer to the message prompt within the min/max range
         */
        @Override
        public int readInt(String msgPrompt, int min, int max) {
            int result;
            do {
                result = readInt(msgPrompt);
            } while (result < min || result > max);

            return result;
        }

        /**
         *
         * A simple method that takes in a message to display on the console,
         * and continually reprompts the user with that message until they enter a long
         * to be returned as the answer to that message.
         *
         * @param msgPrompt - String explaining what information you want from the user.
         * @return the answer to the message as long
         */
        @Override
        public long readLong(String msgPrompt) {
            while (true) {
                try {
                    return Long.parseLong(this.readString(msgPrompt));
                } catch (NumberFormatException e) {
                    this.print("Input error. Please try again.");
                }
            }
        }

        /**
         * A slightly more complex method that takes in a message to display on the console,
         * and continually reprompts the user with that message until they enter a double
         * within the specified min/max range to be returned as the answer to that message.
         *
         * @param msgPrompt - String explaining what information you want from the user.
         * @param min - minimum acceptable value for return
         * @param max - maximum acceptable value for return
         * @return an long value as an answer to the message prompt within the min/max range
         */
        @Override
        public long readLong(String msgPrompt, long min, long max) {
            long result;
            do {
                result = readLong(msgPrompt);
            } while (result < min || result > max);

            return result;
        }

        /**
         *
         * A simple method that takes in a message to display on the console,
         * and continually reprompts the user with that message until they enter a float
         * to be returned as the answer to that message.
         *
         * @param msgPrompt - String explaining what information you want from the user.
         * @return the answer to the message as float
         */
        @Override
        public float readFloat(String msgPrompt) {
            while (true) {
                try {
                    return Float.parseFloat(this.readString(msgPrompt));
                } catch (NumberFormatException e) {
                    this.print("Input error. Please try again.");
                }
            }
        }

        /**
         *
         * A slightly more complex method that takes in a message to display on the console,
         * and continually reprompts the user with that message until they enter a float
         * within the specified min/max range to be returned as the answer to that message.
         *
         * @param msgPrompt - String explaining what information you want from the user.
         * @param min - minimum acceptable value for return
         * @param max - maximum acceptable value for return
         * @return an float value as an answer to the message prompt within the min/max range
         */
        @Override
        public float readFloat(String msgPrompt, float min, float max) {
            float result;
            do {
                result = readFloat(msgPrompt);
            } while (result < min || result > max);

            return result;
        }

        /**
         *
         * A simple method that takes in a message to display on the console,
         * and continually reprompts the user with that message until they enter a double
         * to be returned as the answer to that message.
         *
         * @param msgPrompt - String explaining what information you want from the user.
         * @return the answer to the message as double
         */
        @Override
        public double readDouble(String msgPrompt) {
            while (true) {
                try {
                    return Double.parseDouble(this.readString(msgPrompt));
                } catch (NumberFormatException e) {
                    this.print("Input error. Please try again.");
                }
            }
        }

        /**
         *
         * A slightly more complex method to read double types: It takes in a message to display on the console,
         * and continually reprompts the user with that message until they enter a double
         * within the specified min/max range to be returned as the answer to that message.
         *
         * @param msgPrompt - String explaining what information you want from the user.
         * @param min - minimum acceptable value for return
         * @param max - maximum acceptable value for return
         * @return an double value as an answer to the message prompt within the min/max range
         */
        @Override
        public double readDouble(String msgPrompt, double min, double max) {
            double result;
            do {
                result = readDouble(msgPrompt);
            } while (result < min || result > max);
            return result;
        }

}
