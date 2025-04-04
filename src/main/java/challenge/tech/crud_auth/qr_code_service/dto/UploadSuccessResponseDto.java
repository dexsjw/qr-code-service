package challenge.tech.crud_auth.qr_code_service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UploadSuccessResponseDto extends BaseResponseDto {

    private String dataId;
    private String fileName;

    @Builder(builderMethodName = "UploadSuccessResponseDtoBuilder")
    public UploadSuccessResponseDto(LocalDateTime timestamp, HttpStatusCode status, String message, String dataId, String fileName) {
        super(timestamp, status, message);
        this.dataId = dataId;
        this.fileName = fileName;
    }

}
