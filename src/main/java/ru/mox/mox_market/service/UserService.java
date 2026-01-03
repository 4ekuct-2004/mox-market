package ru.mox.mox_market.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mox.mox_market.dto.dto_out.UserDTO;
import ru.mox.mox_market.entity.authEnt.MoxUser;
import ru.mox.mox_market.repository.UserRepo;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[A-Za-z0-9](?:[A-Za-z0-9_-]{1,14}[A-Za-z0-9])$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,32}$");

    private final UserRepo userRepo;
    private final InventoryService invService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, InventoryService invService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.invService = invService;
        this.passwordEncoder = passwordEncoder;
    }

    public MoxUser getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public MoxUser createAndSaveUser(String username, String password) {
        MoxUser user = MoxUser.create(username, passwordEncoder.encode(password));

        invService.createInventoryForUser(user);
        userRepo.save(user);

        return user;
    }
    public void addRoleToUser(MoxUser user, String role) {
        user.getRoles().add(role);
    }
    public void removeRoleFromUser(MoxUser user, String role) {
        user.getRoles().remove(role);
    }

    public boolean isCredentialsValid(String username, String password) {
        if (username == null || password == null) return false;

        return USERNAME_PATTERN.matcher(username).matches() && PASSWORD_PATTERN.matcher(password).matches();
    }

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream().map(UserDTO::of).collect(Collectors.toList());
    }

    public UserDTO getUserDTO(Long id){
        return UserDTO.of(userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("UserService.getUserDTO | User [" + id + "] not found")));
    }

    @Override
    public MoxUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("UserService.loadByUsername | User [" + username + "] not found"));
    }
}
