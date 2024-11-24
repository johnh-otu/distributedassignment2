package org.example;

import java.rmi.Naming;
import java.util.List;

import org.example.Vote.Choice;

public class Client {
    public static void main(String[] args) {
        try {
            VotingSystem stub = (VotingSystem) Naming.lookup("//localhost/VOTE");

            String userInput = "";
            String[] userInputArray;
            User user = new User(0, "");

            System.out.println("NEW USER\nEnter passkey ");
            userInput = StandardInputUtil.readLine();
            
            for (int i = 0; i < 10; i++) { //try to create new user up to 10 times
                user = new User((int) System.currentTimeMillis() * System.identityHashCode(userInput), userInput);
                try {
                    stub.newUser(user);
                    break;
                } catch (Exception e) {
                    if (i == 9) {
                        System.out.println("Couldn't create user. Shutting down...");
                        return;
                    }
                    System.out.println("Failed to create new user. Retrying...");
                }
            }

            System.out.println("User created successfully.");

            while (!(userInput = StandardInputUtil.readLine()).toLowerCase().equals("quit")) {
                userInputArray = userInput.split(" ", 5);
                processUserInput(userInputArray, stub, user);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void processUserInput(String[] userInputArray, VotingSystem stub, User user) {
        
        if (userInputArray == null || userInputArray.length < 1 || userInputArray[0].equals("")) { return; } //blank line
        
        switch (userInputArray[0].toLowerCase()) {
            case "vote":
                if (userInputArray.length != 3 || (!userInputArray[2].equals("y") && !userInputArray[2].equals("n"))) {
                    System.out.println("Usage: vote <topic> <y/n>");
                    break;
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

                    System.out.println("Enter passkey");
                    String passkey = StandardInputUtil.readLine();
                    stub.castVote(userInputArray[1], user, choice, passkey);
                    System.out.println("Vote submitted!");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    return;
                }
                break;
            case "count":
                if (userInputArray.length != 2) {
                    System.out.println("Usage: count <topic>");
                    break;
                }
                try {
                    int[] results = stub.getVoteCounts(userInputArray[1]);
                    System.out.println(userInputArray[1] + ": Y=" + results[0] + " N=" + results[1]);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    return;
                }
                break;
            case "topic": //topic (lists topics) //topic add topic-name (creates new topic)
                if (userInputArray.length != 1 && !(userInputArray.length == 3 && userInputArray[1].toLowerCase().equals("add"))) {
                    System.out.println("Usage: topic\nor\nUsage: topic add <topic>");
                    break;
                }
                try {
                    if (userInputArray.length == 1) {
                        List<String> topics = stub.getTopics();
                        String out = "TOPICS:\n===================\n";
                        for (String t : topics) {
                            out += t + "\n";
                        }
                        System.out.print(out);
                    } else {
                        stub.createTopic(userInputArray[2]);
                        System.out.println("Topic created successfully.");
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    return;
                }
                break;
            case "help":
                getHelp();
                break;
            default:
                System.out.println(userInputArray[0] + " is not a valid command.\nUse \"help\" to view a list of commands.");
                return;
        }
    }

    private static void getHelp() {
        String[] commands = {
            "vote",
            "count",
            "topic",
            "help"
        };
        String[] descriptions = {
            "Usage: vote <topic> <y/n>",
            "Usage: count <topic>",
            "Usage: topic\n\tor\n\tUsage: topic add <topic>",
            "Usage: help"
        };

        System.out.println("COMMANDS:\n===================");
        for (int i = 0; i < commands.length; i++) {
            System.out.println(commands[i]);
            System.out.println("\t" + descriptions[i]);
        }
    }
}
