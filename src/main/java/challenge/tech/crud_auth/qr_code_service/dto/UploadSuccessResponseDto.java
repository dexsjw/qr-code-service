package challenge.tech.crud_auth.qr_code_service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UploadSuccessResponseDto extends BaseResponseDto {

    private Long qrCodeDataId;
    private String fileName;

    @Builder(builderMethodName = "UploadSuccessResponseDtoBuilder")
    public UploadSuccessResponseDto(LocalDateTime timestamp, HttpStatusCode status, String message,
                                    Long qrCodeDataId, String fileName) {
        super(timestamp, status, message);
        this.qrCodeDataId = qrCodeDataId;
        this.fileName = fileName;
    }

}
