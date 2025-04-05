package challenge.tech.crud_auth.qr_code_service.entity;

import challenge.tech.crud_auth.qr_code_service.repository.QrCodeDataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private QrCodeDataRepository qrCodeDataRepository;

    public DataLoader(QrCodeDataRepository qrCodeDataRepository) {
        this.qrCodeDataRepository = qrCodeDataRepository;
    }

    @PostConstruct
    public void loadData() {
        qrCodeDataRepository.deleteAll();

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
