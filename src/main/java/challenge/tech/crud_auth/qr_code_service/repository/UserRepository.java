package challenge.tech.crud_auth.qr_code_service.repository;

import challenge.tech.crud_auth.qr_code_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
