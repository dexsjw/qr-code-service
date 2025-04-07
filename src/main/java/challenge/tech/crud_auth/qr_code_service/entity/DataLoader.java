package challenge.tech.crud_auth.qr_code_service.entity;

import challenge.tech.crud_auth.qr_code_service.repository.QrCodeDataRepository;
import challenge.tech.crud_auth.qr_code_service.repository.UserAuthRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader {

    private UserAuthRepository userAuthRepository;
    private PasswordEncoder passwordEncoder;
    private QrCodeDataRepository qrCodeDataRepository;

    public DataLoader(UserAuthRepository userAuthRepository, PasswordEncoder passwordEncoder,
                      QrCodeDataRepository qrCodeDataRepository) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
        this.qrCodeDataRepository = qrCodeDataRepository;
    }

    @PostConstruct
    public void loadData() {
        userAuthRepository.deleteAll();
        qrCodeDataRepository.deleteAll();

        List<UserRoleEntity> adminRoles = new ArrayList<>();
        List<UserRoleEntity> userRoles = new ArrayList<>();

        UserRoleEntity adminRole = UserRoleEntity.builder()
                .roleName("ADMIN")
                .description("Administrator")
                .build();

        UserRoleEntity userRole = UserRoleEntity.builder()
                .roleName("AUTH_USER")
                .description("Authenticated User")
                .build();

        adminRoles.add(adminRole);
        userRoles.add(userRole);

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
