package com.discpline.discipline_tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;

    private boolean completed;

    private String date;

    private Long userId;
    private Long habitId;
    public Long getHabitId() {
    return habitId;
}

public void setHabitId(Long habitId) {
    this.habitId = habitId;
}

    public Task() {}

    public Task(Long id, String taskName, boolean completed, String date, Long userId) {
        this.id = id;
        this.taskName = taskName;
        this.completed = completed;
        this.date = date;
        this.userId = userId;
    }

    public Long getId() { return id; }

    public String getTaskName() { return taskName; }

    public boolean isCompleted() { return completed; }

    public String getDate() { return date; }

    public Long getUserId() { return userId; }

    public void setId(Long id) { this.id = id; }

    public void setTaskName(String taskName) { this.taskName = taskName; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public void setDate(String date) { this.date = date; }

    public void setUserId(Long userId) { this.userId = userId; }
}