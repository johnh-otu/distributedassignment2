package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ServerTest {

    @Test 
    public void RMIRegistryTest() {
        assertDoesNotThrow(() -> {
            Registry registry;

            try { 
                registry = LocateRegistry.getRegistry(1099); 
                registry.list(); // This will throw an exception if the registry is not running 
            } catch (Exception e) { 
                registry = LocateRegistry.createRegistry(1099);
            }

            VotingSystem stub = new RemoteVotingSystem();
            Naming.rebind("VOTE", stub);
        });
    }

    @Test
    public void RMIConnectionTest() {
        assertDoesNotThrow(() -> {
            Registry registry;
            
            try { 
                registry = LocateRegistry.getRegistry(1099); 
                registry.list(); // This will throw an exception if the registry is not running 
            } catch (Exception e) { 
                registry = LocateRegistry.createRegistry(1099);
            }

            VotingSystem skeleton = new RemoteVotingSystem();
            Naming.rebind("VOTE", skeleton);
            VotingSystem stub = (VotingSystem) Naming.lookup("VOTE");
            stub.getTopics();
        });
    }
}
