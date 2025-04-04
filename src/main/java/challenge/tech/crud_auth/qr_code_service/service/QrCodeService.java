package challenge.tech.crud_auth.qr_code_service.service;

import challenge.tech.crud_auth.qr_code_service.dto.UploadSuccessResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface QrCodeService {

    UploadSuccessResponseDto processUploadedQrCode(MultipartFile qrCodeFile);

}
