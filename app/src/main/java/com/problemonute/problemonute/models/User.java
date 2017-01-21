package com.problemonute.problemonute.models;

import java.io.Serializable;

/**
 * Created by Hercson on 6/1/2017.
 */

public class User implements Serializable {

    private String username;
    private String gender;
    private int score;

    public User(){

    }

    public User(String username, String gender, int score){
        this.setUsername(username);
        this.setGender(gender);
        this.setScore(score);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
