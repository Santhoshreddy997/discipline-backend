package com.discpline.discipline_tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private int disciplineScore;

    private int streak;

    private int longestStreak;

    private String lastCompletedDate;

    public User() {}

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public int getDisciplineScore() { return disciplineScore; }

    public int getStreak() { return streak; }

    public int getLongestStreak() { return longestStreak; }

    public String getLastCompletedDate() { return lastCompletedDate; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setDisciplineScore(int disciplineScore) { this.disciplineScore = disciplineScore; }

    public void setStreak(int streak) { this.streak = streak; }

    public void setLongestStreak(int longestStreak) { this.longestStreak = longestStreak; }

    public void setLastCompletedDate(String lastCompletedDate) { this.lastCompletedDate = lastCompletedDate; }

}