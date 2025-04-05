package challenge.tech.crud_auth.qr_code_service.dto;

import challenge.tech.crud_auth.qr_code_service.record.QrCodeDataRecord;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class QrCodeDataResponseDto extends BaseResponseDto {

    private QrCodeDataRecord qrCodeData;

    @Builder(builderMethodName = "QrCodeDataResponseDtoBuilder")
    public QrCodeDataResponseDto(LocalDateTime timestamp, HttpStatusCode status, String message,
                                    QrCodeDataRecord qrCodeData) {
        super(timestamp, status, message);
        this.qrCodeData = qrCodeData;
    }

}
