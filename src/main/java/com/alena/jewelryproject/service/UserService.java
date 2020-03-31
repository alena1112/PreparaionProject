package com.alena.jewelryproject.service;

import com.alena.jewelryproject.model.Role;
import com.alena.jewelryproject.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    public static final Role ADMIN_ROLE = new Role("ROLE_ADMIN");
    public static final Role CLIENT_ROLE = new Role("ROLE_CLIENT");
    private List<User> users = new ArrayList() {{
        add(new User("admin", "admin", new HashSet() {{add(ADMIN_ROLE);}}));
        add(new User("client", "", new HashSet() {{add(CLIENT_ROLE);}}));
    }};

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream().filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }
}
