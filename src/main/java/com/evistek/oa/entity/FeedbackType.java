package com.evistek.oa.entity;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/10
 */
public class FeedbackType {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPrefix() {
        return numberPrefix;
    }

    public void setNumberPrefix(String numberPrefix) {
        this.numberPrefix = numberPrefix;
    }

    private String numberPrefix;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    private String director;
}
