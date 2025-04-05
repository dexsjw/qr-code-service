package challenge.tech.crud_auth.qr_code_service.exception;

public class QrCodeDataNotFoundException extends RuntimeException {
    public QrCodeDataNotFoundException(Long id) {
        super("QR Code data could not be found for id: " + id);
    }
}
