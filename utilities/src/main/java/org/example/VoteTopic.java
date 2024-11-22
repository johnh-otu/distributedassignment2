package org.example;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class VoteTopic {
    private String name;
    private HashMap<Integer, Vote> votes; // key: userid, value: vote object

    public VoteTopic(String name) {
        this.name = name;
        votes = new HashMap<Integer, Vote>();
    }

    public String getName() {
        return name;
    }

    public int getVoteCount(Vote.Choice choice) {
        int count = 0;

        for (int i : votes.keySet()) {
            if (votes.get(i).choice.equals(choice)) {
                count++;
            }
        }

        return count;
    }

    public void Vote(Vote vote) throws InvalidParameterException {
        if (hasVoted(vote.caster.getID())) {
            throw new InvalidParameterException("User "+ vote.caster.getID() +" has already voted.");
        }
        votes.put(vote.caster.getID(), vote);
    }

    public boolean hasVoted(int userid) {
        Vote vote = votes.get(userid);
        if (vote == null) {
            return false;
        }
        if (vote.caster.getID() != userid) {
            return false;
        }
        return true;
    }

}
