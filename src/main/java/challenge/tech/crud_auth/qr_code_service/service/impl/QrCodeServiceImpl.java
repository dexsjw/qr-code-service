package challenge.tech.crud_auth.qr_code_service.service.impl;

import challenge.tech.crud_auth.qr_code_service.dto.UploadSuccessResponseDto;
import challenge.tech.crud_auth.qr_code_service.service.QrCodeService;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Override
    public UploadSuccessResponseDto processUploadedQrCode(MultipartFile qrCodeFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(qrCodeFile.getInputStream());
            LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            result.getText();
        } catch (IOException | NotFoundException ex) {
            ex.printStackTrace();

        }

        return UploadSuccessResponseDto.UploadSuccessResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .message("QR Code uploaded successfully")
                .dataId("1")
                .fileName("fileName")
                .build();
    }

}
