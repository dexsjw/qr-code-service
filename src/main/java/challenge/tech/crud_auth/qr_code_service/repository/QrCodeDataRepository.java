package challenge.tech.crud_auth.qr_code_service.repository;

import challenge.tech.crud_auth.qr_code_service.entity.QrCodeDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeDataRepository extends JpaRepository<QrCodeDataEntity, Long> {
}
