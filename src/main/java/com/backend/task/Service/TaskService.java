package com.backend.task.Service;

import com.backend.task.Model.Task;
import com.backend.task.Repository.TaskRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TaskService implements InterfaceTaskService {
    
    @Autowired
    public TaskRepository taskRepo;

    @Override
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public void saveTask(Task task) {
        taskRepo.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepo.deleteById(id);
    }

    @Override
    public Task getOneTask(Long id) {
        return taskRepo.findById(id).orElse(null);
    }

    
    @Override
    public boolean existsById(Long id) {
        return taskRepo.existsById(id);
    }

    @Override
    public boolean existsByNameTask(String nameTask) {
        return taskRepo.existsByNameTask(nameTask);
    }

    @Override
    public Optional<Task> findByNameTask(String nameTask) {
        return taskRepo.findByNameTask(nameTask);
    }
    
}
