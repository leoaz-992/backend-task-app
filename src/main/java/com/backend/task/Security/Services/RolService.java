package com.backend.task.Security.Services;

import com.backend.task.Security.Entity.Rol;
import com.backend.task.Security.Enums.RolNombre;
import com.backend.task.Security.Repositories.RolRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepo;
    
    //busca un rol por su nombre y lo devuelve
    public Optional<Rol> getByRolNombre(RolNombre rolname){
        return rolRepo.findByName(rolname);
    }
}
