package challenge.tech.crud_auth.qr_code_service.controller;

import challenge.tech.crud_auth.qr_code_service.dto.BaseResponseDto;
import challenge.tech.crud_auth.qr_code_service.dto.LoginRequestDto;
import challenge.tech.crud_auth.qr_code_service.entity.UserEntity;
import challenge.tech.crud_auth.qr_code_service.security.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        String username = authentication.getName();
        String jwtToken = jwtTokenUtil.createJwtToken(UserEntity.builder().username(username).build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseDto.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .message("")
                        .build());
    }

}
