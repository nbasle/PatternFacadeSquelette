package com.yaps.petstore.ui.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * This abstract class helps concrete text menu to display information on screen.
 */
public class AbstractTextMenu {

    // ======================================
    // =             Attributes             =
    // ======================================
    static StringTokenizer line;
    private static final String DELIMITER = "-";

    // ======================================
    // =        Presentation Methods        =
    // ======================================
    static StringTokenizer readsInputLine() {
        // Reads the line from the input and put it in a StringTokenizer
        StringTokenizer line = null;
        System.out.print("\t");
        try {
            final BufferedReader msgStream = new BufferedReader(new InputStreamReader(System.in));
            line = new StringTokenizer(msgStream.readLine(), DELIMITER);
        } catch (IOException e) {
            System.out.println("Error : Cannot read the line entered by the user");
        }
        return line;
    }

    static boolean checkNumberOfArguments(final int numberOfArguments) {
        if (line.countTokens() != numberOfArguments) {
            System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
            readsInputLine();
            return false;
        } else {
            return true;
        }
    }

    static void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
    }
}
