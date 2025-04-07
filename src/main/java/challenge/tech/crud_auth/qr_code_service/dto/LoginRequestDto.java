package challenge.tech.crud_auth.qr_code_service.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String username;
    private String password;

}
