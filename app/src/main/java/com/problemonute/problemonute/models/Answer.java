package com.problemonute.problemonute.models;


/**
 * Created by Hercson on 6/1/2017.
 */

public class Answer {
    private String text;
    private boolean right;

    public Answer() {
    }

    public Answer(String text, boolean right) {
        this.text = text;
        this.right = right;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
