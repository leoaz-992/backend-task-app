package com.backend.task.Dto;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

public class DtoTask implements Serializable {
    
    @NotBlank
    private String nameTask;
    private boolean reminder;
    private String dateTask;
    private String descripcionTask;

    public DtoTask() {
    }

    public DtoTask(String nameTask, boolean reminder, String dateTask, String descripcionTask) {
        this.nameTask = nameTask;
        this.reminder = reminder;
        this.dateTask = dateTask;
        this.descripcionTask = descripcionTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public String getDateTask() {
        return dateTask;
    }

    public void setDateTask(String dateTask) {
        this.dateTask = dateTask;
    }

    public String getDescripcionTask() {
        return descripcionTask;
    }

    public void setDescripcionTask(String descripcionTask) {
        this.descripcionTask = descripcionTask;
    }
    
    
}
