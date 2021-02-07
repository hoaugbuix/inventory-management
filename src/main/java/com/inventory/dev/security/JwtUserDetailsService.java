package com.inventory.dev.security;

import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.exception.NotFoundException;
import com.inventory.dev.exception.ResourceNotFoundException;
import com.inventory.dev.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    final static Logger log = Logger.getLogger(JwtUserDetailsService.class);


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(s)
                .orElseThrow(() ->
                        new NotFoundException("User not found [email: " + s + "]")
                );
        if (user != null) {
            return new CustomUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User get email " + s + " does not exist.");
        }
    }
}
