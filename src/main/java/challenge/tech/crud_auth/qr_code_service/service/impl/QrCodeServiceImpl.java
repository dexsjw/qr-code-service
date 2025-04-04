package challenge.tech.crud_auth.qr_code_service.service.impl;

import challenge.tech.crud_auth.qr_code_service.dto.UploadSuccessResponseDto;
import challenge.tech.crud_auth.qr_code_service.service.QrCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Override
    public UploadSuccessResponseDto processUploadedQrCode(MultipartFile qrCodeFile) {
        return UploadSuccessResponseDto.UploadSuccessResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .message("QR Code uploaded successfully")
                .dataId("1")
                .fileName("fileName")
                .build();
    }

}
