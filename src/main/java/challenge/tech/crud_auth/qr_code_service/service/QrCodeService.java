package challenge.tech.crud_auth.qr_code_service.service;

import challenge.tech.crud_auth.qr_code_service.dto.BaseResponseDto;
import challenge.tech.crud_auth.qr_code_service.dto.QrCodeDataResponseDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QrCodeService {

    QrCodeDataResponseDto processUploadedQrCodeFile(MultipartFile qrCodeFile);
    QrCodeDataResponseDto processUploadedQrCodeFiles(List<MultipartFile> qrCodeFiles);
    QrCodeDataResponseDto searchQrCodeData(Long id);
    QrCodeDataResponseDto searchAllQrCodeData();
    ByteArrayResource searchQrCodeImage(Long id);
    BaseResponseDto deleteQrCodeData(Long id);
    BaseResponseDto deleteAllQrCodeData();

}
