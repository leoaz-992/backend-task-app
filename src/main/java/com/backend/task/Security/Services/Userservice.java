package com.backend.task.Security.Services;


import com.backend.task.Security.Entity.UserEntity;
import com.backend.task.Security.Repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Userservice {
    
    @Autowired
    UserRepository  userRepo;
    //busca el usuario por su username y lo retorna
    public Optional<UserEntity> getByUsername(String username){
        return userRepo.findByUsername(username);
    }
    //confirma si existe el username
    public boolean existsUsername(String username){
        return userRepo.existsByUsername(username);
    }
    //confirma si existe el Email
    public boolean existsEmail(String email){
        return userRepo.existsByEmail(email);
    }
    //confirma si existe el id
    public boolean existsId(Long id){
        return userRepo.existsById(id);
    }
    //guarda un nuevo user
    public void saveUser(UserEntity user){
        userRepo.save(user);
    }
    //busca y Elimina un user por su id
    public void deleteUserById(Long id){
        userRepo.deleteById(id);
    }
}
