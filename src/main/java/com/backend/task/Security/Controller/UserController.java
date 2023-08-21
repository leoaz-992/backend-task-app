package com.backend.task.Security.Controller;

import com.backend.task.Model.Task;
import com.backend.task.Security.DTO.UserDTO;
import com.backend.task.Security.Entity.Rol;
import com.backend.task.Security.Entity.UserEntity;
import com.backend.task.Security.Enums.RolNombre;
import com.backend.task.Security.Services.RolService;
import com.backend.task.Security.Services.Userservice;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    Userservice userService;
    @Autowired
    RolService rolService;
    
    
    @PostMapping("/user/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO newUser){
        Set<Rol> roles = new HashSet<>();
        Set<Task> task = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(newUser.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }
       
        UserEntity user= UserEntity.builder()
                .username(newUser.getUsername())
                .password(passwordEncoder.encode( newUser.getPassword()))
                .email(newUser.getEmail())
                .roles(roles)
                .listTasks(task)
                .build();
        
        userService.saveUser(user);
        return ResponseEntity.ok("user creado");
    }
    
    @DeleteMapping("/delete/{id}")// elimina un user buscando por el id
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("borrado");
    }
}
