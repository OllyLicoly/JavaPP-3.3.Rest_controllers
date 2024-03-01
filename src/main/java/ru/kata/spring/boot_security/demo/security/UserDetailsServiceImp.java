package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//        User myUser = userRepository.findByUsername(username);
//        if (myUser == null) {
//            throw new UsernameNotFoundException("Unknown user: " + username);
//        }
//        UserDetails user = User.builder()
//                .username(myUser.getUsername())
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(),
//                user.getAuthorities());
//    }

    @Override
    public UserDetails loadUserByUsername(String username) {
//        return (UserDetails) userRepository.findByUsername(username);
//    }

//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new UserPrincipal(user);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getAuthorities());
    }
}
