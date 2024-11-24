package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VotingSystem extends Remote {
    public void castVote(String topic, User caster, Vote.Choice choice, String passkeyString) throws RemoteException;
    public int[] getVoteCounts(String topic) throws RemoteException;
    public void createTopic(String topic) throws RemoteException;
    public List<String> getTopics() throws RemoteException;
    public void newUser(User user) throws RemoteException;
}
