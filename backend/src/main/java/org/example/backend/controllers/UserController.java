package org.example.backend.controllers;

import org.example.backend.models.RegisterDTO;
import org.example.backend.models.User;
import org.example.backend.models.UserResponseDTO;
import org.example.backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO dto) {
        User user = userService.register(dto);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody RegisterDTO dto){
        User user = userService.login(dto);
        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getTotalPoints(),
                user.getWins()
        );

        return ResponseEntity.ok(response);

    }
}
