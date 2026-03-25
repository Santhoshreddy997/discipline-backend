package com.discpline.discipline_tracker.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.discpline.discipline_tracker.entity.Task;
import com.discpline.discipline_tracker.entity.User;
import com.discpline.discipline_tracker.repository.TaskRepository;
import com.discpline.discipline_tracker.repository.UserRepository;

@Component   // ⚠️ IMPORTANT
public class DailyTaskRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔥 RUN EVERY DAY AT MIDNIGHT
    @Scheduled(cron = "0 0 0 * * ?")
    public void runDailyReset() {

        System.out.println("🔥 Running midnight reset...");

        String today = LocalDate.now().toString();
        String yesterday = LocalDate.now().minusDays(1).toString();

        List<User> users = userRepository.findAll();

        for (User user : users) {

            Long userId = user.getId();

            // 1️⃣ CHECK IF TODAY TASKS EXIST
            List<Task> todayTasks = taskRepository.findByUserIdAndDate(userId, today);

            if (!todayTasks.isEmpty()) {
                continue; // already generated
            }

            // 2️⃣ GET YESTERDAY TASKS
            List<Task> yesterdayTasks = taskRepository.findByUserIdAndDate(userId, yesterday);

            for (Task oldTask : yesterdayTasks) {

                Task newTask = new Task();
                newTask.setTaskName(oldTask.getTaskName());
                newTask.setCompleted(false);
                newTask.setDate(today);
                newTask.setUserId(userId);

                taskRepository.save(newTask);
            }

            // 3️⃣ RESET TODAY SCORE
            user.setDisciplineScore(0);

            userRepository.save(user);
        }

        System.out.println("✅ Midnight reset completed");
    }
}