package challenge.tech.crud_auth.qr_code_service.service.impl;

import challenge.tech.crud_auth.qr_code_service.dto.BaseResponseDto;
import challenge.tech.crud_auth.qr_code_service.dto.QrCodeDataResponseDto;
import challenge.tech.crud_auth.qr_code_service.entity.QrCodeDataEntity;
import challenge.tech.crud_auth.qr_code_service.repository.QrCodeDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class QrCodeServiceImplTest {

    @Mock
    private QrCodeDataRepository qrCodeDataRepository;

    @InjectMocks
    private QrCodeServiceImpl qrCodeService;

    private MockMultipartFile mockMultipartFile;
    private QrCodeDataEntity qrCodeDataEntity;

    @BeforeEach
    public void init() {
        Path qrCodeFile = Paths.get("src/main/resources/static/Android_Url_QR_Code.png");
        try {
            mockMultipartFile = new MockMultipartFile("qrCodeFile", Files.newInputStream(qrCodeFile));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        qrCodeDataEntity = QrCodeDataEntity.builder()
                .id(1L)
                .fileName("Android_Url_QR_Code.png")
                .data("https://www.android.com/")
                .build();
    }

    @Test
    public void processUploadedQrCodeFileSuccessTest() {
        when(qrCodeDataRepository.save(qrCodeDataEntity)).thenReturn(qrCodeDataEntity);
        QrCodeDataResponseDto qrCodeDataResponseDto = qrCodeService.processUploadedQrCodeFile(mockMultipartFile);

        assertEquals(HttpStatus.CREATED, qrCodeDataResponseDto.getStatus());
        assertEquals("QR code image uploaded successfully", qrCodeDataResponseDto.getMessage());
    }

    @Test
    public void searchQrCodeImageSuccessTest() {
        Long id = 1L;
        when(qrCodeDataRepository.findById(id)).thenReturn(Optional.of(qrCodeDataEntity));
        qrCodeDataEntity.getData().getBytes();
    }

    @Test
    public void deleteQrCodeDataSuccessTest() {
        Long id = 1L;
        BaseResponseDto expectedBaseResponseDto = BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .message("QR code data deleted successfully for id: " + id)
                .build();
        BaseResponseDto actualBaseResponseDto = qrCodeService.deleteQrCodeData(id);

        assertEquals(expectedBaseResponseDto.getStatus(), actualBaseResponseDto.getStatus());
        assertEquals(expectedBaseResponseDto.getMessage(), actualBaseResponseDto.getMessage());
    }

    @Test
    public void deleteAllQrCodeDataSuccesTest() {
        BaseResponseDto expectedBaseResponseDto = BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .message("All QR code data deleted successfully")
                .build();
        BaseResponseDto actualBaseResponseDto = qrCodeService.deleteAllQrCodeData();

        assertEquals(expectedBaseResponseDto.getStatus(), actualBaseResponseDto.getStatus());
        assertEquals(expectedBaseResponseDto.getMessage(), actualBaseResponseDto.getMessage());
    }

}
