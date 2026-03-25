package com.discpline.discipline_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.discpline.discipline_tracker.entity.Habit;
import com.discpline.discipline_tracker.entity.Task;
import com.discpline.discipline_tracker.repository.HabitRepository;
import com.discpline.discipline_tracker.repository.TaskRepository;

@RestController
@RequestMapping("/habits")
@CrossOrigin(origins = "http://localhost:3000")
public class HabitController {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private TaskRepository taskRepository;

    // =========================
    // CREATE HABIT + TASK
    // =========================
    @PostMapping
    public Habit addHabit(@RequestBody Habit habit) {

        Habit savedHabit = habitRepository.save(habit);

        // 🔥 CREATE TODAY TASK IMMEDIATELY
        Task task = new Task();
        task.setTaskName(savedHabit.getName());
        task.setCompleted(false);
        task.setDate(LocalDate.now().toString());
        task.setUserId(savedHabit.getUserId());
        task.setHabitId(savedHabit.getId());

        taskRepository.save(task);

        return savedHabit;
    }

    // =========================
    // GET HABITS
    // =========================
    @GetMapping("/user/{userId}")
    public List<Habit> getHabits(@PathVariable Long userId) {
        return habitRepository.findByUserId(userId);
    }

    // =========================
    // DELETE HABIT + TASKS
    // =========================
    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id) {

        // 🔥 delete related tasks FIRST
        taskRepository.deleteTasksByHabitId(id);

        // then delete habit
        habitRepository.deleteById(id);
    }

    // =========================
    // UPDATE HABIT
    // =========================
    @PutMapping("/{id}")
    public Habit updateHabit(@PathVariable Long id, @RequestBody Habit updatedHabit) {

        Habit habit = habitRepository.findById(id).orElseThrow();

        habit.setName(updatedHabit.getName());

        return habitRepository.save(habit);
    }
}