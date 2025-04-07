package challenge.tech.crud_auth.qr_code_service.repository;

import challenge.tech.crud_auth.qr_code_service.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
