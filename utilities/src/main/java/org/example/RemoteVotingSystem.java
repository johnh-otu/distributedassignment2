package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RemoteVotingSystem extends UnicastRemoteObject implements VotingSystem {

    private HashMap<String, VoteTopic> topics;
    private HashMap<Integer, User> users;

    protected RemoteVotingSystem() throws RemoteException {
        super();
        topics = new HashMap<String, VoteTopic>();
        users = new HashMap<Integer, User>();
    }

    @Override
    public void castVote(String topic, User caster, Vote.Choice choice, String passkeyString) throws InvalidParameterException  {
        User user = users.get(caster.getID());
        if (user == null) {
            throw new InvalidParameterException("User does not exist.");
        }
        Vote vote = new Vote(user, choice, passkeyString);
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
    public List<String> getTopics() {
        return new ArrayList<String>(topics.keySet());
    }

    @Override
    public void newUser(User user) throws RemoteException {
        if (users.keySet().contains(user.getID())) {
            throw new RemoteException("User already exists. Please try again.");
        }
        users.put(user.getID(), user);
    }
    
    
}
