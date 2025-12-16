package ru.mox.mox_market.entity.authEnt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mox.mox_market.entity.BaseEntity;
import ru.mox.mox_market.entity.tradeEnt.Inventory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity @Getter @Setter @NoArgsConstructor
public class MoxUser extends BaseEntity implements UserDetails {

    private String publicName;
    private String avatarUrl;
    private String status;
    private String aboutMe;

    @OneToOne(fetch = FetchType.EAGER)
    private Inventory inventory;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isEnabled() {
        return !isDeleted();
    }

    public static MoxUser create(String username, String password) {
        MoxUser moxUser = new MoxUser();
        moxUser.setPublicName(username);
        moxUser.setAvatarUrl("static/images/avatars/default/1.png");
        moxUser.setStatus("");
        moxUser.setAboutMe("");
        moxUser.setUsername(username);
        moxUser.setPassword(password);

        Set<String> roles = new HashSet<>();
        roles.add("USER");
        moxUser.setRoles(roles);

        return moxUser;
    }
}
