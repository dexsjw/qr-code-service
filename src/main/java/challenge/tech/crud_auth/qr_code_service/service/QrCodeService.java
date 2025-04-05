package challenge.tech.crud_auth.qr_code_service.service;

import challenge.tech.crud_auth.qr_code_service.dto.BaseResponseDto;
import challenge.tech.crud_auth.qr_code_service.dto.QrCodeDataResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QrCodeService {

    QrCodeDataResponseDto processUploadedQrCodeFile(MultipartFile qrCodeFile);
    BaseResponseDto processUploadedQrCodeFiles(List<MultipartFile> qrCodeFiles);
    QrCodeDataResponseDto searchQrCodeData(Long id);
    byte[] searchQrCodeImage(Long id);
    BaseResponseDto deleteQrCodeData(Long id);
    BaseResponseDto deleteAllQrCodeData();

}
