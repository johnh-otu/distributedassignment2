package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class RemoteVotingSystem extends UnicastRemoteObject implements VotingSystem {

    private HashMap<String, VoteTopic> topics;
    private ArrayList<User> users;

    protected RemoteVotingSystem() throws RemoteException {
        super();
        topics = new HashMap<String, VoteTopic>();
        users = new ArrayList<User>();
    }

    @Override
    public void castVote(String topic, Vote vote) throws InvalidParameterException  {
        VoteTopic voteTopic = topics.get(topic);
        if (voteTopic == null) {
            throw new InvalidParameterException("Topic \"" + topic + "\" does not exist.");
        }
        voteTopic.Vote(vote);
    }

    @Override
    public int[] getVoteCounts(String topic) throws InvalidParameterException {
        VoteTopic voteTopic = topics.get(topic);
        if (voteTopic == null) {
            throw new InvalidParameterException("Topic \"" + topic + "\" does not exist.");
        }
        
        int[] output = {0,0};
        output[0] = voteTopic.getVoteCount(Vote.Choice.YEA);
        output[1] = voteTopic.getVoteCount(Vote.Choice.NAY);
        return output;
    }

    @Override
    public void createTopic(String topic) throws InvalidParameterException {
        if (getTopics().contains(topic)) {
            throw new InvalidParameterException("Topic \"" + topic + "\" already exists.");
        }
        topics.put(topic, new VoteTopic(topic));
    }

    @Override
    public Set<String> getTopics() {
        return topics.keySet();
    }

    @Override
    public void authenticateUser(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticateUser'");
    }
    
    
}
