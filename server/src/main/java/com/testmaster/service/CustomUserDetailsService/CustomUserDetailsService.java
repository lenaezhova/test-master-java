package com.testmaster.service.CustomUserDetailsService;

import com.testmaster.model.User;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с email: " + email + " не найден")
        );

        if (!user.getIsActivate()) {
            throw new UsernameNotFoundException("Пользователь не активирован");
        }

        if (user.getDeleted()) {
            throw new UsernameNotFoundException("Пользователь удален");
        }

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());

        return new CustomUserDetails(user, authorities);
    }
}
