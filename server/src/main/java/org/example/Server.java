package org.example;

import java.io.IOException;
import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            RemoteVotingSystem stub = new RemoteVotingSystem();
            Naming.rebind("VOTE",stub);
            System.out.println("Server started");
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        String userInput;
        String[] userInputArray;

        try {
            while (!(userInput = StandardInputUtil.readLine()).toLowerCase().equals("quit")) {
                userInputArray = userInput.split(" ", 2);
            }
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}