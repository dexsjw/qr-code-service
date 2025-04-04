package challenge.tech.crud_auth.qr_code_service.service.impl;

import challenge.tech.crud_auth.qr_code_service.dto.UploadSuccessResponseDto;
import challenge.tech.crud_auth.qr_code_service.entity.QrCodeDataEntity;
import challenge.tech.crud_auth.qr_code_service.repository.QrCodeDataRepository;
import challenge.tech.crud_auth.qr_code_service.service.QrCodeService;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    private QrCodeDataRepository qrCodeDataRepository;

    public QrCodeServiceImpl(QrCodeDataRepository qrCodeDataRepository) {
        this.qrCodeDataRepository = qrCodeDataRepository;
    }

    @Override
    public UploadSuccessResponseDto processUploadedQrCode(MultipartFile qrCodeFile) {
        String fileName = qrCodeFile.getOriginalFilename();
        Long qrCodeDataId;

        try {
            BufferedImage bufferedImage = ImageIO.read(qrCodeFile.getInputStream());
            LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            String qrCodeData = result.getText();
            qrCodeDataId = saveQrCodeData(fileName, qrCodeData);
        } catch (IOException | NotFoundException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        String message = qrCodeDataId == null
                ? "QR Code uploaded unsuccessfully"
                : "QR Code uploaded successfully for file: " + fileName;

        return UploadSuccessResponseDto.UploadSuccessResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .message(message)
                .qrCodeDataId(qrCodeDataId)
                .fileName(fileName)
                .build();
    }

    private Long saveQrCodeData(String fileName, String qrCodeData) {
        QrCodeDataEntity qrCodeDataEntity = QrCodeDataEntity.builder()
                .fileName(fileName)
                .data(qrCodeData)
                .build();
        return qrCodeDataRepository.save(qrCodeDataEntity).getId();
    }

}
