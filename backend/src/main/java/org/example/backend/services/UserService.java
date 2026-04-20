package org.example.backend.services;

import org.example.backend.models.RegisterDTO;
import org.example.backend.models.Role;
import org.example.backend.models.User;
import org.example.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder pwdEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.pwdEncoder = passwordEncoder;
    }

    public User register (RegisterDTO dto){
        //Checks if user exists
        if(userRepo.findByUsername(dto.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists.");
        }
        //Create user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(pwdEncoder.encode(dto.getPassword()));
        user.setTotalPoints(0);
        user.setWins(0);
        user.setRole(Role.USER);

        return userRepo.save(user);
    }

    public User login (RegisterDTO dto){

        //Check if user doesnt exist
        if(userRepo.findByUsername(dto.getUsername()).isEmpty()){
            throw new RuntimeException("User doesnt exist");
        }

        //After check that user exists we need to check if pwd is correct
        User user = userRepo.findByUsername(dto.getUsername()).get();

        if(pwdEncoder.matches(dto.getPassword(), user.getPassword())){
            return user;
        } else {
            throw new RuntimeException("Invalid password.");
        }
    }
}
