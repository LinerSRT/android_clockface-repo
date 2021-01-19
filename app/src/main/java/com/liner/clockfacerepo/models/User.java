package com.liner.clockfacerepo.models;

import java.io.Serializable;

public class User implements Serializable {
    public Type type;
    public String UID;
    public String deviceUID;
    public String name;
    public String email;
    public String password;

    //TODO Check if user is privileged (needs to disable ads) or use Google API Credentials

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "type=" + type +
                ", UID='" + UID + '\'' +
                ", deviceUID='" + deviceUID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public enum Type {
        USER,
        ADMIN
    }
}
