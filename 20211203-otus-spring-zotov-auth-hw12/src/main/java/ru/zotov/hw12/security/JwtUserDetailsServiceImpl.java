package ru.zotov.hw12.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.zotov.hw12.dao.UserLibraryRepository;
import ru.zotov.hw12.domain.JwtUser;
import ru.zotov.hw12.domain.UserLibrary;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    private final UserLibraryRepository userLibraryRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLibrary user = userLibraryRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));

        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ADMIN")));
    }
}
