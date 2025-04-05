package challenge.tech.crud_auth.qr_code_service.controller;

import challenge.tech.crud_auth.qr_code_service.dto.BaseResponseDto;
import challenge.tech.crud_auth.qr_code_service.dto.QrCodeDataResponseDto;
import challenge.tech.crud_auth.qr_code_service.service.QrCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/qr-code")
public class QrCodeController {

    private QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<QrCodeDataResponseDto> processUploadedQrCodeFile(@RequestPart MultipartFile qrCodeFile) {
        return ResponseEntity.status(HttpStatus.CREATED).body(qrCodeService.processUploadedQrCodeFile(qrCodeFile));
    }

    @PostMapping(path = "/upload/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponseDto> processUploadedQrCodeFiles(@RequestPart List<MultipartFile> qrCodeFiles) {
        return ResponseEntity.status(HttpStatus.CREATED).body(qrCodeService.processUploadedQrCodeFiles(qrCodeFiles));
    }

    @GetMapping(path = "/data/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QrCodeDataResponseDto> searchQrCodeData(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.searchQrCodeData(id));
    }

    @GetMapping(path = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> searchQrCodeImage(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.searchQrCodeImage(id));
    }

    @DeleteMapping(path = "/data/{id}")
    public ResponseEntity<BaseResponseDto> deleteQrCodeData(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.deleteQrCodeData(id));
    }

    @DeleteMapping(path = "/data/all")
    public ResponseEntity<BaseResponseDto> deleteAllQrCodeData() {
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.deleteAllQrCodeData());
    }

}
