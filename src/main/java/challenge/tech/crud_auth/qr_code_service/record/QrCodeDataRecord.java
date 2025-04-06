package challenge.tech.crud_auth.qr_code_service.record;

import lombok.Builder;

@Builder
public record QrCodeDataRecord(Long id, String fileName, String data) {
}
