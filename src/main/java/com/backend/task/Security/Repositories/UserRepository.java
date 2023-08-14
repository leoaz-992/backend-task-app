package com.backend.task.Security.Repositories;

import com.backend.task.Security.Entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    //busca un user por el username y lo devuelve
    Optional<UserEntity> findByUsername(String username);
    //confirma que existe un username
    boolean existsByUsername( String username);
    //confirma si existe el email
    boolean existsByEmail( String email);
}
