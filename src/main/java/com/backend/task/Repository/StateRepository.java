package com.backend.task.Repository;

import com.backend.task.Model.State;
import com.backend.task.Model.StatusTask;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<StatusTask,Long> {
    public Optional<StatusTask> findByNameState(State nameState);
}
