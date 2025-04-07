package challenge.tech.crud_auth.qr_code_service.service.impl;

import challenge.tech.crud_auth.qr_code_service.entity.UserAuthEntity;
import challenge.tech.crud_auth.qr_code_service.repository.UserAuthRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class QrCodeUserDetailsService implements UserDetailsService {

    private UserAuthRepository userAuthRepository;

    public QrCodeUserDetailsService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthEntity userAuthEntity = userAuthRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(""));

        return User.builder()
                .username(userAuthEntity.getUsername())
                .password(userAuthEntity.getPassword())
                .roles("AUTH_USER")
                .build();
    }

}
