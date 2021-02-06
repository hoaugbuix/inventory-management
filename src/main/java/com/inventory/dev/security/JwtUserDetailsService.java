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
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    final static Logger log = Logger.getLogger(JwtUserDetailsService.class);


    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepository.findAllByEmail(s)
                .orElseThrow(() ->
                        new NotFoundException("User not found [email: " + s + "]")
                );
        log.info("user" + user.toString());

        if (user != null) {
            return new CustomUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User get email " + s + " does not exist.");
        }
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return new CustomUserDetails(user);
    }
}
