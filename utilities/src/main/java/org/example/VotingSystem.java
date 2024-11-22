package org.example;

import java.security.InvalidParameterException;
import java.util.Set;

public interface VotingSystem {
    public void castVote(String topic, Vote vote) throws InvalidParameterException;
    public int[] getVoteCounts(String topic) throws InvalidParameterException;
    public void createTopic(String topic) throws InvalidParameterException;
    public Set<String> getTopics();
    public void authenticateUser(String key);
}
