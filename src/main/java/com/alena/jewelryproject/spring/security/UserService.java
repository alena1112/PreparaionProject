package com.alena.jewelryproject.spring.security;

import com.alena.jewelryproject.model.Role;
import com.alena.jewelryproject.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    public static final Role ADMIN_ROLE = new Role("ROLE_ADMIN");
    public static final Role CLIENT_ROLE = new Role("ROLE_CLIENT");
    private List<User> users;

    public UserService(@Value("${user.admin.password}") String adminPassword) {
        this.users = new ArrayList() {{
            add(new User("admin", adminPassword, new HashSet() {{add(ADMIN_ROLE);}}));
            add(new User("client", null, new HashSet() {{add(CLIENT_ROLE);}}));
        }};
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream().filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    public User loadAdminUser(String username, String password) {
        return users.stream().filter(user ->
                user.getUsername().equals(username) && user.getPassword().equals(password) &&
                        user.getRoles().stream()
                                .filter(role -> role.equals(ADMIN_ROLE))
                                .findFirst().orElse(null) != null)
                .findFirst().orElse(null);
    }
}
