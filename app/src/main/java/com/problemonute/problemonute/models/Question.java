package com.problemonute.problemonute.models;



/**
 * Created by Hercson on 6/1/2017.
 */

public class Question {

    private String text;
    private long points;
    private int difficulty_level;
    private String category;
    //private ArrayList<Answer> answers;

    public Question() {
    }

    public Question(String text, long points, int difficulty_level, String category) {
        this.text = text;
        this.points = points;
        this.difficulty_level = difficulty_level;
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public int getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(int difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
