package ru.mox.mox_market.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mox.mox_market.dto.dto_out.UserDTO;
import ru.mox.mox_market.dto.in_dto.UserRegisterRequest;
import ru.mox.mox_market.entity.authEnt.MoxUser;
import ru.mox.mox_market.service.UserService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserDTO> getMe(Authentication authentication) {
        if(authentication == null) { return ResponseEntity.notFound().build(); }

        MoxUser user = (MoxUser) authentication.getPrincipal();
        return ResponseEntity.ok(UserDTO.of(user));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(UserDTO.of(userService.getUserById(userId)));
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRegisterRequest registerRequest) {
        if(!userService.isCredentialsValid(registerRequest.username(), registerRequest.password())) { return ResponseEntity.badRequest().build(); }

        MoxUser user = userService.createAndSaveUser(registerRequest.username(), registerRequest.password());
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(UserDTO.of(user));
    }

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
