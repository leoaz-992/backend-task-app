package com.backend.task.Service;

import com.backend.task.Model.Task;
import com.backend.task.Security.Entity.UserEntity;
import java.util.List;
import java.util.Optional;

public interface InterfaceTaskService {
    public List<Task> getAllTasks();
    public List<Task> getAllTasksOfUser(UserEntity user);
    public void saveTask(Task task);
    public void deleteTask(Long id);
    public Task getOneTask(Long id);
    public boolean existsById(Long id);
    public Optional<Task> findByNameTask(String nameTask);
    public boolean existsByNameTask(String name);
}
