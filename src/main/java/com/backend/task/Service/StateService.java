package com.backend.task.Service;

import com.backend.task.Model.State;
import com.backend.task.Model.StatusTask;
import com.backend.task.Repository.StateRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StateService {
    
    @Autowired
     StateRepository stateRepo;
    
    public Optional<StatusTask> getByState(State name){
        return stateRepo.findByNameState(name);
    }
    
    
}
