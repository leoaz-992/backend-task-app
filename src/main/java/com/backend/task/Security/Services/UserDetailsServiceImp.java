package com.backend.task.Security.Services;

import com.backend.task.Security.Entity.UserEntity;
import com.backend.task.Security.Repositories.UserRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    private UserRepository userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario "+username+"no existe."));
        
        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());
        
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                true,
                true,
                true,true,authorities);
    }
    
}
