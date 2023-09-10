
package com.backend.task.Repository;

import com.backend.task.Model.Task;
import com.backend.task.Security.Entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>{
    public Optional<Task> findByNameTask(String nameTask);
    public boolean existsByNameTask(String nameTask);
    public List<Task> findAllByUser(UserEntity user);
}
