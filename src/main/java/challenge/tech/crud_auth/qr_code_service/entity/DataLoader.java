package challenge.tech.crud_auth.qr_code_service.entity;

import challenge.tech.crud_auth.qr_code_service.repository.QrCodeDataRepository;
import challenge.tech.crud_auth.qr_code_service.repository.UserAuthRepository;
import challenge.tech.crud_auth.qr_code_service.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader {

    private UserAuthRepository userAuthRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private QrCodeDataRepository qrCodeDataRepository;

    public DataLoader(UserAuthRepository userAuthRepository, UserRoleRepository userRoleRepository,
                      PasswordEncoder passwordEncoder, QrCodeDataRepository qrCodeDataRepository) {
        this.userAuthRepository = userAuthRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.qrCodeDataRepository = qrCodeDataRepository;
    }

    @PostConstruct
    public void loadData() {
        userAuthRepository.deleteAll();
//        userRoleRepository.deleteAll();
        qrCodeDataRepository.deleteAll();

        List<UserRoleEntity> adminRoles = new ArrayList<>();
        List<UserRoleEntity> userRoles = new ArrayList<>();
        List<UserRoleEntity> publicRoles = new ArrayList<>();

        UserRoleEntity adminRole = UserRoleEntity.builder()
                .roleName("ADMIN")
                .description("Administrator")
                .build();

        UserRoleEntity userRole = UserRoleEntity.builder()
                .roleName("AUTH_USER")
                .description("Authenticated User")
                .build();

        UserRoleEntity publicRole = UserRoleEntity.builder()
                .roleName("PUBLIC_USER")
                .description("Public User")
                .build();

        adminRoles.add(adminRole);
        userRoles.add(userRole);
        publicRoles.add(publicRole);

        userAuthRepository.save(UserAuthEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("adminPw"))
                .userRoles(adminRoles)
                .build());

        userAuthRepository.save(UserAuthEntity.builder()
                .username("authUser")
                .password(passwordEncoder.encode("authUserPw"))
                .userRoles(userRoles)
                .build());

        userAuthRepository.save(UserAuthEntity.builder()
                .username("publicUser")
                .password(passwordEncoder.encode("publicUserPw"))
                .userRoles(publicRoles)
                .build());

//        userRoleRepository.save(adminRole);
//        userRoleRepository.save(userRole);
//        userRoleRepository.save(publicRole);

        qrCodeDataRepository.save(QrCodeDataEntity.builder()
                .fileName("Google_Url_QR_Code.png")
                .data("https://www.google.com/")
                .build());

        qrCodeDataRepository.save(QrCodeDataEntity.builder()
                .fileName("YouTube_Url_QR_Code.png")
                .data("https://www.youtube.com/")
                .build());

        qrCodeDataRepository.save(QrCodeDataEntity.builder()
                .fileName("GitHub_Url_QR_Code.png")
                .data("https://github.com/")
                .build());
    }

}
