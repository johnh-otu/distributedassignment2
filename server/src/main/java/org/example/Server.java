package org.example;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        VotingSystem stub;
        try {
            LocateRegistry.createRegistry(1099);
            stub = new RemoteVotingSystem();
            Naming.rebind("VOTE",stub);
            System.out.println("Server started");
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        String userInput;
        String[] userInputArray;

        try {
            while (!(userInput = StandardInputUtil.readLine()).toLowerCase().equals("quit")) {
                userInputArray = userInput.split(" ", 2);
            }
            System.exit(0);
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}