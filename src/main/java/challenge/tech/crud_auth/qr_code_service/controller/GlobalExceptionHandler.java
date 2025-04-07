package challenge.tech.crud_auth.qr_code_service.controller;

import challenge.tech.crud_auth.qr_code_service.dto.BaseResponseDto;
import challenge.tech.crud_auth.qr_code_service.dto.ErrorResponseDto;
import challenge.tech.crud_auth.qr_code_service.exception.QrCodeDataNotFoundException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
        log.error(ex.getMessage(), ex.getCause(), ex.getStackTrace());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.ErrorResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Wrong credentials provided")
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        log.error(ex.getMessage(), ex.getCause(), ex.getStackTrace());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.ErrorResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED)
                .message("JWT is not a signed Claims")
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDto> handleJwtException(JwtException ex) {
        log.error(ex.getMessage(), ex.getCause(), ex.getStackTrace());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.ErrorResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED)
                .message("Invalid JWT token")
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }

    @ExceptionHandler(QrCodeDataNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleQrCodeDataNotFoundException(QrCodeDataNotFoundException ex) {
        log.error(ex.getMessage(), ex.getCause(), ex.getStackTrace());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.ErrorResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message("Error finding QR code data")
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage(), ex.getCause(), ex.getStackTrace());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.ErrorResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message("Error finding QR code in image")
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(WriterException.class)
    public ResponseEntity<ErrorResponseDto> handleWriterException(WriterException ex) {
        log.error(ex.getMessage(), ex.getCause(), ex.getStackTrace());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.ErrorResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Error writing to OutputStream")
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDto> handleIOException(IOException ex) {
        log.error(ex.getMessage(), ex.getCause(), ex.getStackTrace());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.ErrorResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Error occurred for I/O operation")
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        log.error("An unknown Exception has occurred!");
        log.error(ex.getMessage(), ex.getCause(), ex.getStackTrace());
        ErrorResponseDto errorResponse = ErrorResponseDto.ErrorResponseDtoBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Something went wrong")
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
