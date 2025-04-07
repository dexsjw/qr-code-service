package challenge.tech.crud_auth.qr_code_service.service.impl;

import challenge.tech.crud_auth.qr_code_service.entity.UserAuthEntity;
import challenge.tech.crud_auth.qr_code_service.entity.UserRoleEntity;
import challenge.tech.crud_auth.qr_code_service.repository.UserAuthRepository;
import challenge.tech.crud_auth.qr_code_service.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QrCodeUserDetailsService implements UserDetailsService {

    private UserAuthRepository userAuthRepository;
    private UserRoleRepository userRoleRepository;

    public QrCodeUserDetailsService(UserAuthRepository userAuthRepository, UserRoleRepository userRoleRepository) {
        this.userAuthRepository = userAuthRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthEntity userAuthEntity = userAuthRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        List<String> userRoleNames = userAuthEntity.getUserRoles().stream()
                .map(UserRoleEntity::getRoleName)
                .toList();

        return User.builder()
                .username(userAuthEntity.getUsername())
                .password(userAuthEntity.getPassword())
                .roles(userRoleNames.toArray(String[]::new))
                .build();
    }

}
