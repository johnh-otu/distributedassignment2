package org.example;

import java.util.List;

public interface VotingSystem {
    public void castVote(VoteTopic t, Vote v);
    public void getVoteCount(VoteTopic t);
    public void createTopic(VoteTopic t);
    public List<VoteTopic> getTopics();
    public void authenticateUser(String key);
}
