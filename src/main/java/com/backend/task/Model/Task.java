package com.backend.task.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nameTask;
    private boolean reminder;
    private String dateTask;
    private String descripcionTask;

    public Task() {
    }

    public Task(String nameTask, boolean reminder, String dateTask, String descripcionTask) {
        this.nameTask = nameTask;
        this.reminder = reminder;
        this.dateTask = dateTask;
        this.descripcionTask = descripcionTask;
    }
    
}
