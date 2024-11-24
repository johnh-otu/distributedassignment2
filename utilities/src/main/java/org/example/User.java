package org.example;

import java.io.Serializable;

public class User implements Serializable {
    int id;
    String passkeyString;

    public User(int id, String passkeyString) {
        this.id = id;
        this.passkeyString = passkeyString;
    }

    public int getID() {
        return id;
    }
    public boolean passKeyIs(String passkey) {
        return passkey.equals(passkeyString);
    }
}
