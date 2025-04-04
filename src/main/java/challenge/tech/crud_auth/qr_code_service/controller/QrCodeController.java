package challenge.tech.crud_auth.qr_code_service.controller;

import challenge.tech.crud_auth.qr_code_service.dto.UploadSuccessResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/qr-code")
public class QrCodeController {

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadSuccessResponseDto> processUploadedQrCode(@RequestPart MultipartFile qrCodeFile) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generateSuccessResponseDto());
    }

    private UploadSuccessResponseDto generateSuccessResponseDto() {
        return UploadSuccessResponseDto.UploadSuccessResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .message("QR Code uploaded successfully")
                .dataId("1")
                .fileName("fileName")
                .build();
    }

}
