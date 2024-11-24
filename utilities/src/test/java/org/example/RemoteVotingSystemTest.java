package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RemoteVotingSystemTest {

    RemoteVotingSystem votingSystem;

    @BeforeEach
    public void SetRemote() {
        try {
            votingSystem = new RemoteVotingSystem();   
        } catch (Exception e) {}
    }

    @Test
    public void testVote13() {
        assertThrows(InvalidParameterException.class, () -> {
            User user = new User(13, "13");
            votingSystem.castVote(null, user, null, null);
        });
    }
    @Test
    public void testVote124() {
        assertThrows(InvalidParameterException.class, () -> {
            User user = new User(0, "password");
            votingSystem.newUser(user);
            votingSystem.castVote(null, user, null, "not password");
        });
    }
    @Test
    public void testVote1257() {
        assertThrows(InvalidParameterException.class, () -> {
            User user =new User(0, "password");
            votingSystem.newUser(user);
            votingSystem.castVote(null, user, null, "password");
        });
    }
    @Test
    public void testVote1256() {
        assertDoesNotThrow(() -> {
            User user =new User(0, "password");
            votingSystem.createTopic("topic");
            votingSystem.newUser(user);
            votingSystem.castVote("topic", user, Vote.Choice.YEA, "password");
        });
    }
    @Test
    public void testVoteYea() {
        User user = new User(1, "yea");
        try {
            votingSystem.newUser(user);
            votingSystem.createTopic("voteyea");
            votingSystem.castVote("voteyea", user, Vote.Choice.YEA, "yea");
        } catch (Exception e) {fail(e.getMessage());}
        int[] results = {-1, -1};
        try { 
            results = votingSystem.getVoteCounts("voteyea");
        } catch (Exception e) {fail(e.getMessage());}
        assertEquals(1, results[0]);
        assertEquals(0, results[1]);
    }
    @Test
    public void testVoteNay() {
        User user = new User(2, "nay");
        try {
            votingSystem.newUser(user);
            votingSystem.createTopic("votenay");
            votingSystem.castVote("votenay", user, Vote.Choice.NAY, "nay");
        } catch (Exception e) {fail(e.getMessage());}
        int[] results = {-1, -1};
        try { 
            results = votingSystem.getVoteCounts("votenay");
        } catch (Exception e) {fail(e.getMessage());}
        assertEquals(0, results[0]);
        assertEquals(1, results[1]);
    }
    
    @Test
    public void testVoteCounts() {
        int[] results = {-1, -1};
        try {
            User user = new User(3, "count");
            votingSystem.newUser(user);
            votingSystem.createTopic("count");
            votingSystem.castVote("count", user, Vote.Choice.YEA, "count");
            results = votingSystem.getVoteCounts("count");
        } catch (Exception e) { fail(e.getMessage()); }
        assertEquals(1, results[0]);
        assertEquals(0, results[1]);
    }
    @Test
    public void testVoteCountsNone() {
        int[] results = {-1, -1};
        try {
            votingSystem.createTopic("countNone");
            results = votingSystem.getVoteCounts("countNone");
        } catch (Exception e) { fail(e.getMessage()); }
        assertEquals(0, results[0]);
        assertEquals(0, results[1]);
    }
    
    @Test
    public void testCreateExistingTopic() {
        try {
            votingSystem.createTopic("redo");
        } catch (Exception e) { fail(e.getMessage()); }
        assertThrows(InvalidParameterException.class, () -> {
            votingSystem.createTopic("redo");
        });
    }
    
    @Test
    public void testCreateGetTopics() {
        List<String> topicsExpected = new ArrayList<String>(){
            {
                add("topic1");
                add("topic2");
                add("topic3");
            }
        };
        List<String> topics = new ArrayList<>();
        try {
            votingSystem.createTopic(topicsExpected.get(0));
            votingSystem.createTopic(topicsExpected.get(1));
            votingSystem.createTopic(topicsExpected.get(2));
            topics = votingSystem.getTopics();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(topicsExpected, topics);
    }
    
    @Test
    public void testNewUser() {
        User user = new User(5, "user");
        try {
            votingSystem.newUser(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertTrue(votingSystem.userExists(user.getID()));
        assertTrue(votingSystem.checkPasskey(user.getID(), user.passkeyString));
    }
    @Test
    public void testRepeatUser() {
        User user = new User(6, "repeatuser");
        try {
            votingSystem.newUser(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertThrows(RemoteException.class, () -> {
            votingSystem.newUser(user);
        });
    }
}
