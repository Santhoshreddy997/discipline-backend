package com.discpline.discipline_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.discpline.discipline_tracker.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // =========================
    // GET USER TASKS
    // =========================
    List<Task> findByUserId(Long userId);

    // =========================
    // GET TODAY TASKS
    // =========================
    List<Task> findByUserIdAndDate(Long userId, String date);

    // =========================
    // DELETE TASKS BY HABIT (SAFE VERSION)
    // =========================
    @Transactional
    @Modifying
    @Query("DELETE FROM Task t WHERE t.habitId = :habitId")
    void deleteTasksByHabitId(Long habitId);
}