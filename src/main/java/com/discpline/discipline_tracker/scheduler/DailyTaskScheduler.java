package com.discpline.discipline_tracker.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.discpline.discipline_tracker.entity.Task;
import com.discpline.discipline_tracker.repository.TaskRepository;

@Component
public class DailyTaskScheduler {

    @Autowired
    private TaskRepository taskRepository;

    @Scheduled(cron = "0 0 0 * * ?") // every day at midnight
    public void resetTasks(){

        List<Task> tasks = taskRepository.findAll();

        for(Task task : tasks){
            task.setCompleted(false);
        }

        taskRepository.saveAll(tasks);

        System.out.println("Daily task reset completed");
    }

}