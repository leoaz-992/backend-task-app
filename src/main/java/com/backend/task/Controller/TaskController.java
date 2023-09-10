package com.backend.task.Controller;

import com.backend.task.Dto.DtoTask;
import com.backend.task.Model.State;
import com.backend.task.Model.Task;
import com.backend.task.Security.Entity.UserEntity;
import com.backend.task.Security.Services.Userservice;
import com.backend.task.Service.InterfaceTaskService;
import com.backend.task.Service.StateService;
import io.micrometer.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
//@CrossOrigin("")
public class TaskController {
    
    @Autowired
    private InterfaceTaskService taskService;
    
    @Autowired
    private Userservice  userServ;
    
    @Autowired
    private StateService stateServ;
    
    @PostMapping("/new")//crea una tarea nueva
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTask(@RequestBody DtoTask task){
     //verifica si una tarea con el mismo nombre ya existe.
        if(taskService.existsByNameTask(task.getNameTask())){
             return new ResponseEntity(new Mensaje("La tarea ya existe."), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(task.getNameUser())){
            return new ResponseEntity(new Mensaje("La tarea debe ser asignada a un usuario, El usuario no puede estar vacio."), HttpStatus.BAD_REQUEST);
        }
        if(!userServ.existsUsername(task.getNameUser())){
            return new ResponseEntity(new Mensaje("El usuario asignado no exixte."), HttpStatus.BAD_REQUEST);
        }
     //crea una nueva tarea y añade los datos enviados.
        Task tasknew = new Task();
        tasknew.setNameTask(task.getNameTask());
        tasknew.setDescripcionTask(task.getDescripcionTask());
        tasknew.setReminder(task.isReminder());
        tasknew.setDateTask(task.getDateTask());
        tasknew.setUser(userServ.getByUser(task.getNameUser()).orElse(null));
        // Le agrega el estado "asignado" a la tarea
        tasknew.setState(stateServ.getByState(State.ASSIGNED).get());
     //guarda la nueva tarea en la db
        taskService.saveTask(tasknew);
     //retorna un mensaje de OK
        return new ResponseEntity(new Mensaje("Tarea Creada"), HttpStatus.OK);
    }
    
    @GetMapping("/list") //Lista todas las tareas 
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> ListOfTasks(){
       List<Task> listTasks= taskService.getAllTasks();
       return new ResponseEntity(listTasks, HttpStatus.OK); 
    }
    
    @GetMapping("/{user}/list")//envia la lista de tareas de un usuario que recibe como parametro
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> TaskOfUser(@PathVariable("user") String user){
        if(!userServ.existsUsername(user)){
            return new ResponseEntity(new Mensaje("No existe el usuario") ,HttpStatus.BAD_REQUEST);
        }
        
        UserEntity userTask = userServ.getByUser(user).orElse(null);
        List<Task> listTasks = taskService.getAllTasksOfUser(userTask);
        return new ResponseEntity(listTasks, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")// elimina una tarea buscandola por el id
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
     //busca si existe el id recibido 
        if(!taskService.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.NOT_FOUND);
        }
        Task taskDeleted = taskService.getOneTask(id);
        //CONFIRMA Y solo elimina la tarea si esta finalizada u oculta.
        if(!taskDeleted.getState().equals(stateServ.getByState(State.FINALIZED).get())&& !taskDeleted.getState().equals(stateServ.getByState(State.HIDDEN).get()) ){
            return new ResponseEntity(new Mensaje("Tarea no esta finalizada"), HttpStatus.BAD_REQUEST);
        }
        //elimina la tarea con el id recibido
        taskService.deleteTask(id);
        return new ResponseEntity(new Mensaje("Tarea eliminada"), HttpStatus.OK);
    }
    
    @PutMapping("edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editarTask(@PathVariable Long id, @RequestBody DtoTask task){
      //verifica que la tarea con el id recibido exista
        if(!taskService.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.NOT_FOUND);
        }
      //verifica que el nombre de la tarea no se repita 
        if(taskService.existsByNameTask(task.getNameTask()) && !Objects.equals(taskService.findByNameTask(task.getNameTask()).get().getId(), id)){
            return new ResponseEntity(new Mensaje("Ese nombre de la tarea ya existe"), HttpStatus.BAD_REQUEST);
        }
      //verifica que el nombre de la tarea no se envie en blanco
        if(StringUtils.isBlank(task.getNameTask())){
            return new ResponseEntity(new Mensaje("El nombre de la tarea no pueden estar vacio"), HttpStatus.BAD_REQUEST);
        }
        if(!userServ.existsUsername(task.getNameUser()) && !"".equals(task.getNameUser())){
            return new ResponseEntity(new Mensaje("El ususario asignado no exixte."), HttpStatus.BAD_REQUEST);
        }
      //Se Actualiza los datos y se guardan en la db
        Task editTask =taskService.getOneTask(id);
        editTask.setNameTask(task.getNameTask());
        editTask.setDescripcionTask(task.getDescripcionTask());
        editTask.setReminder(task.isReminder());
        editTask.setDateTask(task.getDateTask());
        editTask.setUser(userServ.getByUser(task.getNameUser()).orElse(null));
        //cambia el estados de la tarea
        if("".equals(task.getNameUser())){
            editTask.setState(stateServ.getByState(State.NO_ASSIGNAMENT).get());
            task.setState("NO_ASSIGNAMENT");
        } 
        switch(task.getState()){
            case "" -> editTask.setState(stateServ.getByState(State.ASSIGNED).get());
            case "NO_ASSIGNAMENT" -> editTask.setState(stateServ.getByState(State.NO_ASSIGNAMENT).get());
            case "ASSIGNED" -> editTask.setState(stateServ.getByState(State.ASSIGNED).get());
            case "IN PROGRESS" -> editTask.setState(stateServ.getByState(State.IN_PROGRESS).get());
            case "FINALIZED" -> editTask.setState(stateServ.getByState(State.FINALIZED).get());
            case "HIDDEN" -> editTask.setState(stateServ.getByState(State.HIDDEN).get());
            default -> {
                return new ResponseEntity(new Mensaje("el estado es incorrecto"), HttpStatus.BAD_REQUEST);
            }
        }
        taskService.saveTask(editTask);

        return new ResponseEntity(new Mensaje("tarea actualizada"), HttpStatus.OK);
    }
    
    
    @PatchMapping("edit/{id}") //edita solo el estado de una tarea
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> editStateTask(@PathVariable Long id, @RequestBody DtoTask task){
       //verifica que la tarea con el id recibido exista
        if(!taskService.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe la tarea solicitada."), HttpStatus.NOT_FOUND);
        } 
        Task taskModificate =taskService.getOneTask(id);
        //cambia el estados de la tarea
        switch(task.getState()){
            case "ASSIGNED" -> taskModificate.setState(stateServ.getByState(State.ASSIGNED).get());
            case "IN PROGRESS" -> taskModificate.setState(stateServ.getByState(State.IN_PROGRESS).get());
            case "FINALIZED" -> taskModificate.setState(stateServ.getByState(State.FINALIZED).get());
            case "HIDDEN" -> taskModificate.setState(stateServ.getByState(State.HIDDEN).get());
            default -> {
                return new ResponseEntity(new Mensaje("el estado no es valido"), HttpStatus.BAD_REQUEST);
            }
        }
        taskService.saveTask(taskModificate);         
        return new ResponseEntity(new Mensaje("El estado de la tarea se  actualizo correctamente"), HttpStatus.OK);   
    }
}
