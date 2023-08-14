/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backend.task.Security.Repositories;

import com.backend.task.Security.Entity.Rol;
import com.backend.task.Security.Enums.RolNombre;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long>{
    //busca el rol por su nombre y lo devuelve
    Optional<Rol> findByName(RolNombre name);
}
