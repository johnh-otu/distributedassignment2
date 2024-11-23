package org.example;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;

import org.example.Utils;
import org.example.Vote.Choice;

public class Client {
    public static void main(String[] args) {
        try {
            VotingSystem stub = (VotingSystem) Naming.lookup("VOTE");

            String userInput;
            String[] userInputArray;
            while (!(userInput = StandardInputUtil.readLine()).toLowerCase().equals("quit")) {
                userInputArray = userInput.split(" ", 2);
                processUserInput(userInputArray, stub);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void processUserInput(String[] userInputArray, VotingSystem stub) {
        
        if (userInputArray == null || userInputArray.length < 1) { return; } //blank line
        
        switch (userInputArray[0].toLowerCase()) {
            case "vote":
                if (userInputArray.length != 3 || (!userInputArray[2].equals("y") && !userInputArray[2].equals("n"))) {
                    System.out.println("Usage: vote <topic> <y/n>");
                }
                try {
                    Choice choice;
                    switch (userInputArray[2]) {
                        case "y":
                            choice = Choice.YEA;
                            break;
                        case "n":
                            choice = Choice.NAY;
                            break;
                        default:
                            System.out.println("Usage: vote <topic> <y/n>");
                            return;
                    }

                    Vote vote = new Vote(null, choice); //TODO : Add user
                    stub.castVote(userInputArray[1], vote);
                } catch (Exception e) {
                    System.err.println("ERROR: " + e.getMessage());
                    return;
                }
                break;
            case "counts":
                if (userInputArray.length != 2) {
                    System.out.println("Usage: counts <topic>");
                }
                try {
                    int[] results = stub.getVoteCounts(userInputArray[1]);
                    System.out.println(userInputArray[1] + ": Y=" + results[0] + " N=" + results[1]);
                } catch (Exception e) {
                    System.err.println("ERROR: " + e.getMessage());
                    return;
                }
                break;
            case "topic": //topic (lists topics) //topic add topic-name (creates new topic)
                if (userInputArray.length != 1 && !(userInputArray.length == 3 && userInputArray[1].toLowerCase().equals("add"))) {
                    System.out.println("Usage: topic\nor\nUsage: topic add <topic>");
                }
                try {
                    if (userInputArray.length == 1) {
                        Set<String> topics = stub.getTopics();
                        String out = "TOPICS:\n===================\n";
                        for (String t : topics) {
                            out += t + "\n";
                        }
                        System.out.print(out);
                    } else {
                        stub.createTopic(userInputArray[2]);
                    }
                } catch (Exception e) {
                    System.err.println("ERROR: " + e.getMessage());
                    return;
                }
                break;
            case "login":
                throw new UnsupportedOperationException("login command is not implemented.");
            default:
                return;
        }
        
    }
}
