package challenge.tech.crud_auth.qr_code_service.service.impl;

import challenge.tech.crud_auth.qr_code_service.dto.BaseResponseDto;
import challenge.tech.crud_auth.qr_code_service.dto.QrCodeDataResponseDto;
import challenge.tech.crud_auth.qr_code_service.entity.QrCodeDataEntity;
import challenge.tech.crud_auth.qr_code_service.exception.QrCodeDataNotFoundException;
import challenge.tech.crud_auth.qr_code_service.record.QrCodeDataRecord;
import challenge.tech.crud_auth.qr_code_service.repository.QrCodeDataRepository;
import challenge.tech.crud_auth.qr_code_service.service.QrCodeService;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    private QrCodeDataRepository qrCodeDataRepository;

    public QrCodeServiceImpl(QrCodeDataRepository qrCodeDataRepository) {
        this.qrCodeDataRepository = qrCodeDataRepository;
    }

    @Override
    public QrCodeDataResponseDto processUploadedQrCodeFile(MultipartFile qrCodeFile) {
        String fileName = qrCodeFile.getOriginalFilename();
        String qrCodeData = processQrCodeFile(qrCodeFile);
        QrCodeDataEntity qrCodeDataEntity = saveQrCodeData(fileName, qrCodeData);
        QrCodeDataRecord qrCodeDataRecord = new QrCodeDataRecord(qrCodeDataEntity.getId(),
                qrCodeDataEntity.getFileName(), qrCodeDataEntity.getData());

        return QrCodeDataResponseDto.QrCodeDataResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .message("QR Code uploaded successfully")
                .qrCodeData(qrCodeDataRecord)
                .build();
    }

    @Override
    public BaseResponseDto processUploadedQrCodeFiles(List<MultipartFile> qrCodeFiles) {
        StringBuilder sb = new StringBuilder();
        for (MultipartFile qrCodeFile : qrCodeFiles) {
            String fileName = qrCodeFile.getOriginalFilename();
            String qrCodeData = processQrCodeFile(qrCodeFile);
            Long qrCodeDataId = saveQrCodeData(fileName, qrCodeData).getId();

            sb.append("{ qrCodeDataId: ").append(qrCodeDataId).append(" - ");
            sb.append("fileName: ").append(fileName).append(" }, ");
        }

        return BaseResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .message("All QR code files have been uploaded successfully. Details: "
                        + sb.deleteCharAt(sb.length() - 2))
                .build();
    }

    @Override
    public QrCodeDataResponseDto searchQrCodeData(Long id) {
        QrCodeDataEntity qrCodeDataEntity = qrCodeDataRepository.findById(id).orElseThrow(() -> new QrCodeDataNotFoundException(id));
        QrCodeDataRecord qrCodeDataRecord = new QrCodeDataRecord(qrCodeDataEntity.getId(),
                qrCodeDataEntity.getFileName(), qrCodeDataEntity.getData());

        return QrCodeDataResponseDto.QrCodeDataResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("QR code data found")
                .qrCodeData(qrCodeDataRecord)
                .build();
    }

    @Override
    public byte[] searchQrCodeImage(Long id) {
        QrCodeDataEntity qrCodeDataEntity = qrCodeDataRepository.findById(id).orElseThrow(() -> new QrCodeDataNotFoundException(id));
        return generateQrCode(qrCodeDataEntity.getData());
    }

    @Override
    public BaseResponseDto deleteQrCodeData(Long id) {
        qrCodeDataRepository.deleteById(id);

        return BaseResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("QR code data deleted successfully for id: " + id)
                .build();
    }

    @Override
    public BaseResponseDto deleteAllQrCodeData() {
        qrCodeDataRepository.deleteAll();

        return BaseResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("All QR code data deleted successfully")
                .build();
    }

    private String processQrCodeFile(MultipartFile qrCodeFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(qrCodeFile.getInputStream());
            LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            return result.getText();
        } catch (IOException | NotFoundException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private QrCodeDataEntity saveQrCodeData(String fileName, String qrCodeData) {
        QrCodeDataEntity qrCodeDataEntity = QrCodeDataEntity.builder()
                .fileName(fileName)
                .data(qrCodeData)
                .build();
        return qrCodeDataRepository.save(qrCodeDataEntity);
    }

    private byte[] generateQrCode(String data) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix qrCodeBitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 250, 250);
            BufferedImage qrCodeBufferedImage = MatrixToImageWriter.toBufferedImage(qrCodeBitMatrix);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCodeBufferedImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (WriterException | IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
