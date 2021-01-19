package com.liner.clockfacerepo.models;

import java.io.Serializable;
import java.util.Arrays;

public class Watchface implements Serializable {
    public String UID;
    public String name;
    public String shortDescription;
    public String description;
    public String[] tags;
    public String[] features;
    public String[] featuredUsersUIDs;
    public int views;
    public int downloads;

    public Watchface() {
    }


    @Override
    public String toString() {
        return "Watchface{" +
                "UID='" + UID + '\'' +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", features=" + Arrays.toString(features) +
                ", featuredUsersUIDs=" + Arrays.toString(featuredUsersUIDs) +
                ", views=" + views +
                ", downloads=" + downloads +
                '}';
    }
}
