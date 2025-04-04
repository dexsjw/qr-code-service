package challenge.tech.crud_auth.qr_code_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDto {

    private LocalDateTime timestamp;
    private HttpStatusCode status;
    private String message;

}
