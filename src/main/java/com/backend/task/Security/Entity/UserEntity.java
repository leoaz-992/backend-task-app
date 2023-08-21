package com.backend.task.Security.Entity;

import com.backend.task.Model.Task;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class UserEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long  id;
    
    @Email
    @NotBlank
    @Size(max=80)
    private String email;
    
    @NotBlank
    @Size(max=80)
    private String username;
    
    @NotBlank
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Rol.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name ="user_id"), inverseJoinColumns = @JoinColumn(name ="role_id") )
    private Set<Rol> roles;
    
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH},orphanRemoval = true)
    @Column(nullable=true)
    private Set<Task> listTasks;
}
