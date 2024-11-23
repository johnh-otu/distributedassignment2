package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Set;

public interface VotingSystem extends Remote {
    public void castVote(String topic, Vote vote) throws RemoteException;
    public int[] getVoteCounts(String topic) throws RemoteException;
    public void createTopic(String topic) throws RemoteException;
    public List<String> getTopics() throws RemoteException;
    public void authenticateUser(String key) throws RemoteException;
}
