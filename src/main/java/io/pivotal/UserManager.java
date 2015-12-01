package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

/**
 * Created by pivotal on 11/30/15.
 */

@Component
public class UserManager implements UserDetailsManager {
    @Autowired
    UserRepository userRepository;

    public void createUser(UserDetails user){
        userRepository.save((io.pivotal.User) user);
    }

    public void updateUser(UserDetails user) {
        userRepository.save((io.pivotal.User) user);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete((io.pivotal.User) user);
        }
    }

    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(newPassword);
        }
    }

    public boolean userExists(String username) {
        User user = userRepository.findByUsername(username);
        return (user != null);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}