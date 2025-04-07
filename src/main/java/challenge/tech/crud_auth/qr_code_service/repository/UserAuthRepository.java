package challenge.tech.crud_auth.qr_code_service.repository;

import challenge.tech.crud_auth.qr_code_service.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Long> {
    Optional<UserAuthEntity> findByUsername(String username);
}
