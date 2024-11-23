package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StandardInputUtil {
    
    private static BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    public static String readLine() throws IOException {
        try {
            System.out.print("> ");
            String input = stdIn.readLine();
            return input;
        } catch (Exception e) {
            throw e;
        }
    }
}