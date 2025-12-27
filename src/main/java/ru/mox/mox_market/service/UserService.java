package ru.mox.mox_market.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mox.mox_market.dto.dto_out.UserDTO;
import ru.mox.mox_market.entity.authEnt.MoxUser;
import ru.mox.mox_market.repository.UserRepo;

import java.util.Set;

@Service
public class UserService implements UserDetailsService {

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
    public MoxUser getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    public void createAndSaveUser(String username, String password) {
        MoxUser user = MoxUser.create(username, passwordEncoder.encode(password));

        invService.createInventoryForUser(user);
        userRepo.save(user);
    }
    public void addRoleToUser(MoxUser user, String role) {
        Set<String> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
    }
    public void removeRoleFromUser(MoxUser user, String role) {
        Set<String> roles = user.getRoles();
        roles.remove(role);
        user.setRoles(roles);
    }

    public UserDTO getUserDTO(Long id){
        return UserDTO.of(userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("UserService.getUserDTO | User [" + id + "] not found")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("UserService.loadByUsername | User [" + username + "] not found"));
    }
}
