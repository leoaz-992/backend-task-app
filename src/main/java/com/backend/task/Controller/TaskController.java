package com.backend.task.Controller;

import com.backend.task.Dto.DtoTask;
import com.backend.task.Model.Task;
import com.backend.task.Service.InterfaceTaskService;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @PostMapping("/new")//crea una tarea nueva
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTask(@RequestBody DtoTask task){
     //verifica si una tarea con el mismo nombre ya existe.
        if(taskService.existsByNameTask(task.getNameTask())){
             return new ResponseEntity(new Mensaje("La tarea ya existe."), HttpStatus.BAD_REQUEST);
        }
     //crea una nueva tarea y añade los datos enviados.
        Task tasknew = new Task();
        tasknew.setNameTask(task.getNameTask());
        tasknew.setDescripcionTask(task.getDescripcionTask());
        tasknew.setReminder(task.isReminder());
        tasknew.setDateTask(task.getDateTask());
     //guarda la nueva tarea en la db
        taskService.saveTask(tasknew);
     //retorna un mensaje de OK
        return new ResponseEntity(new Mensaje("Tarea Creada"), HttpStatus.OK);
    }
    
    @GetMapping("/list") //Lista todas las tareas 
    
    public ResponseEntity<List<Task>> ListOfTasks(){
       List<Task> listTasks= taskService.getAllTasks();
       return new ResponseEntity(listTasks, HttpStatus.OK); 
    }
    
    @DeleteMapping("/delete/{id}")// elimina una tarea buscandola por el id
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
     //busca si existe el id recibido 
        if(!taskService.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.NOT_FOUND);
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
        if(taskService.existsByNameTask(task.getNameTask()) && taskService.findByNameTask(task.getNameTask()).get().getId()!= id){
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
      //verifica que el nombre de la tarea no se envie en blanco
        if(StringUtils.isBlank(task.getNameTask())){
            return new ResponseEntity(new Mensaje("El nombre de la tarea no pueden estar vacio"), HttpStatus.BAD_REQUEST);
        }
      //Se Actualiza los datos y se guardan en la db
        Task editTask =taskService.getOneTask(id);
        editTask.setNameTask(task.getNameTask());
        editTask.setDescripcionTask(task.getDescripcionTask());
        editTask.setReminder(task.isReminder());
        editTask.setDateTask(task.getDateTask());
        taskService.saveTask(editTask);

        return new ResponseEntity(new Mensaje("tarea actualizada"), HttpStatus.OK);
    }
}
