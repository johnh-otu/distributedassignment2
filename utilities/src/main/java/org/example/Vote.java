package org.example;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class Vote implements Serializable {
    enum Choice {
        YEA,
        NAY
    }

    public final User caster;
    public final Vote.Choice choice;

    public Vote(User caster, Vote.Choice choice, String passkey) throws InvalidParameterException {
        if (!caster.passKeyIs(passkey)) {
            throw new InvalidParameterException("Authentication failed.");
        }

        this.caster = caster;
        this.choice = choice;
    }
}
