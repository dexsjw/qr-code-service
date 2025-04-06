package challenge.tech.crud_auth.qr_code_service.dto;

import challenge.tech.crud_auth.qr_code_service.record.QrCodeDataRecord;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto extends BaseResponseDto {

    private String errorMessage;

    @Builder(builderMethodName = "ErrorResponseDtoBuilder")
    public ErrorResponseDto(LocalDateTime timestamp, HttpStatusCode status, String message,
                                 String errorMessage) {
        super(timestamp, status, message);
        this.errorMessage = errorMessage;
    }

}
