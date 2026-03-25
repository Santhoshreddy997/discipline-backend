package com.discpline.discipline_tracker.dto;

public class WeeklyScoreDTO {

    private String date;
    private int score;

    public WeeklyScoreDTO(String date, int score) {
        this.date = date;
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }
}