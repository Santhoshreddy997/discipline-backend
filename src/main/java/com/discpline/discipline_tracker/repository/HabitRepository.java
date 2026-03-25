package com.discpline.discipline_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.discpline.discipline_tracker.entity.Habit;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    List<Habit> findByUserId(Long userId);
}