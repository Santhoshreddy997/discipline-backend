package com.discpline.discipline_tracker.controller;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.discpline.discipline_tracker.entity.Task;
import com.discpline.discipline_tracker.entity.User;
import com.discpline.discipline_tracker.entity.Habit;
import com.discpline.discipline_tracker.repository.TaskRepository;
import com.discpline.discipline_tracker.repository.UserRepository;
import com.discpline.discipline_tracker.repository.HabitRepository;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HabitRepository habitRepository;

    // ================================
    // CREATE TASK (optional manual)
    // ================================
    @PostMapping
    public Task addTask(@RequestBody Task task) {

        task.setDate(LocalDate.now().toString());
        task.setCompleted(false);

        return taskRepository.save(task);
    }

    // ================================
    // GET TODAY TASKS
    // ================================
    @GetMapping("/user/{userId}")
    public List<Task> getUserTasks(@PathVariable Long userId) {

        generateTodayTasks(userId);        // 🔥 create today's tasks
        updateScoreAndStreak(userId);      // 🔥 update score + streak

        String today = LocalDate.now().toString();

        return taskRepository.findByUserIdAndDate(userId, today);
    }

    // ================================
    // COMPLETE TASK
    // ================================
    @PutMapping("/{taskId}/complete")
    public Task completeTask(@PathVariable Long taskId) {

        Task task = taskRepository.findById(taskId).orElseThrow();

        task.setCompleted(true);
        taskRepository.save(task);

        updateScoreAndStreak(task.getUserId());

        return task;
    }

    // ================================
    // GENERATE TODAY TASKS FROM HABITS
    // ================================
    private void generateTodayTasks(Long userId) {

        String today = LocalDate.now().toString();

        List<Task> todayTasks = taskRepository.findByUserIdAndDate(userId, today);

        if (!todayTasks.isEmpty()) {
            return; // already generated
        }

        List<Habit> habits = habitRepository.findByUserId(userId);

        for (Habit habit : habits) {

            Task task = new Task();
            task.setTaskName(habit.getName());
            task.setCompleted(false);
            task.setDate(today);
            task.setUserId(userId);
            task.setHabitId(habit.getId());

            taskRepository.save(task);
        }
    }

    // ================================
    // SCORE + STREAK LOGIC
    // ================================
    private void updateScoreAndStreak(Long userId) {

        String today = LocalDate.now().toString();
        String yesterday = LocalDate.now().minusDays(1).toString();

        // ===== TODAY SCORE =====
        List<Task> todayTasks = taskRepository.findByUserIdAndDate(userId, today);

        int totalToday = todayTasks.size();
        int completedToday = (int) todayTasks.stream()
                .filter(Task::isCompleted)
                .count();

        int todayScore = 0;

        if (totalToday > 0) {
            todayScore = (completedToday * 100) / totalToday;
        }

        User user = userRepository.findById(userId).orElseThrow();
        user.setDisciplineScore(todayScore);

        // ===== STREAK (YESTERDAY BASED) =====
        List<Task> yesterdayTasks = taskRepository.findByUserIdAndDate(userId, yesterday);

        int totalYesterday = yesterdayTasks.size();
        int completedYesterday = (int) yesterdayTasks.stream()
                .filter(Task::isCompleted)
                .count();

        int yesterdayScore = 0;

        if (totalYesterday > 0) {
            yesterdayScore = (completedYesterday * 100) / totalYesterday;
        }

        if (yesterdayScore == 100) {

            if (user.getLastCompletedDate() == null ||
                !user.getLastCompletedDate().equals(yesterday)) {

                user.setStreak(user.getStreak() + 1);

                if (user.getStreak() > user.getLongestStreak()) {
                    user.setLongestStreak(user.getStreak());
                }

                user.setLastCompletedDate(yesterday);
            }

        } else {
            user.setStreak(0);
        }

        userRepository.save(user);
    }

    // ================================
    // WEEKLY PROGRESS
    // ================================
    @GetMapping("/weekly/{userId}")
    public List<Map<String, Object>> getWeeklyProgress(@PathVariable Long userId) {

        List<Task> tasks = taskRepository.findByUserId(userId);

        Map<String, Integer> scoreMap = new HashMap<>();
        Map<String, Integer> totalMap = new HashMap<>();

        for (Task task : tasks) {

            String date = task.getDate();

            totalMap.put(date, totalMap.getOrDefault(date, 0) + 1);

            if (task.isCompleted()) {
                scoreMap.put(date, scoreMap.getOrDefault(date, 0) + 1);
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();

        for (String date : totalMap.keySet()) {

            int total = totalMap.get(date);
            int completed = scoreMap.getOrDefault(date, 0);

            int score = (completed * 100) / total;

            Map<String, Object> entry = new HashMap<>();
            entry.put("date", date);
            entry.put("score", score);

            result.add(entry);
        }

        return result;
    }
}