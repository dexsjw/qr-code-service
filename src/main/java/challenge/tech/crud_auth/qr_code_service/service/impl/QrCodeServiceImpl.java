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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    private QrCodeDataRepository qrCodeDataRepository;

    public QrCodeServiceImpl(QrCodeDataRepository qrCodeDataRepository) {
        this.qrCodeDataRepository = qrCodeDataRepository;
    }

    @Override
    public QrCodeDataResponseDto processUploadedQrCodeFile(MultipartFile qrCodeFile) {
        QrCodeDataEntity qrCodeDataEntity = processFileAndSaveQrCodeData(qrCodeFile);

        return QrCodeDataResponseDto.QrCodeDataResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .message("QR code image uploaded successfully")
                .qrCodeData(QrCodeDataRecord.builder()
                        .id(qrCodeDataEntity.getId())
                        .fileName(qrCodeDataEntity.getFileName())
                        .data(qrCodeDataEntity.getData())
                        .build())
                .build();
    }

    @Override
    public QrCodeDataResponseDto processUploadedQrCodeFiles(List<MultipartFile> qrCodeFiles) {
        List<QrCodeDataRecord> qrCodeDataRecordList = new ArrayList<>();

        for (MultipartFile qrCodeFile : qrCodeFiles) {
            QrCodeDataEntity qrCodeDataEntity = processFileAndSaveQrCodeData(qrCodeFile);
            qrCodeDataRecordList.add(QrCodeDataRecord.builder()
                    .id(qrCodeDataEntity.getId())
                    .fileName(qrCodeDataEntity.getFileName())
                    .data(qrCodeDataEntity.getData())
                    .build());
        }

        return QrCodeDataResponseDto.QrCodeDataResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .message("QR code images uploaded successfully")
                .qrCodeDataList(qrCodeDataRecordList)
                .build();
    }

    @Override
    public QrCodeDataResponseDto searchQrCodeData(Long id) {
        QrCodeDataEntity qrCodeDataEntity = qrCodeDataRepository.findById(id)
                .orElseThrow(() -> new QrCodeDataNotFoundException(id));

        return QrCodeDataResponseDto.QrCodeDataResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("QR code data found")
                .qrCodeData(QrCodeDataRecord.builder()
                        .id(qrCodeDataEntity.getId())
                        .fileName(qrCodeDataEntity.getFileName())
                        .data(qrCodeDataEntity.getData())
                        .build())
                .build();
    }

    @Override
    public QrCodeDataResponseDto searchAllQrCodeData() {
        List<QrCodeDataRecord> qrCodeDataRecordList = new ArrayList<>();
        List<QrCodeDataEntity> qrCodeDataEntityList = qrCodeDataRepository.findAll();

        for (QrCodeDataEntity qrCodeDataEntity : qrCodeDataEntityList) {
            qrCodeDataRecordList.add(QrCodeDataRecord.builder()
                    .id(qrCodeDataEntity.getId())
                    .fileName(qrCodeDataEntity.getFileName())
                    .data(qrCodeDataEntity.getData())
                    .build());
        }

        return QrCodeDataResponseDto.QrCodeDataResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .message("Found all QR code data")
                .qrCodeDataList(qrCodeDataRecordList)
                .build();
    }

    @Override
    public ByteArrayResource searchQrCodeImage(Long id) {
        QrCodeDataEntity qrCodeDataEntity = qrCodeDataRepository.findById(id)
                .orElseThrow(() -> new QrCodeDataNotFoundException(id));
        return generateQrCodeImage(qrCodeDataEntity.getData());
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

    private QrCodeDataEntity processFileAndSaveQrCodeData(MultipartFile qrCodeFile) {
        String fileName = qrCodeFile.getOriginalFilename();
        String qrCodeData = processQrCodeFile(qrCodeFile);
        return qrCodeDataRepository.save(QrCodeDataEntity.builder()
                .fileName(fileName)
                .data(qrCodeData)
                .build());
    }

    private String processQrCodeFile(MultipartFile qrCodeFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(qrCodeFile.getInputStream());
            LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            return result.getText();
        } catch (IOException | NotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private ByteArrayResource generateQrCodeImage(String data) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix qrCodeBitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 250, 250);
            BufferedImage qrCodeBufferedImage = MatrixToImageWriter.toBufferedImage(qrCodeBitMatrix);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCodeBufferedImage, "png", byteArrayOutputStream);
            return new ByteArrayResource(byteArrayOutputStream.toByteArray());
        } catch (WriterException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
