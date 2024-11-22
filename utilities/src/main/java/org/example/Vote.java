package org.example;

public class Vote {
    enum Choice {
        YEA,
        NAY
    }

    public final User caster;
    public final Vote.Choice choice;

    public Vote(User caster, Vote.Choice choice) {
        this.caster = caster;
        this.choice = choice;
    }
}
