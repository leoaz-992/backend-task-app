package com.backend.task.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoTask {
    
    @NotBlank
    private String nameTask;
    private boolean reminder;
    private String dateTask;
    private String descripcionTask;
    @NotBlank
    private String state;
    private String nameUser;


}
