package com.backend.task.Security.DTO;

import com.backend.task.Model.Task;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    
    private Set<String> roles;
    
    private Set<Task> listTasks;
}
