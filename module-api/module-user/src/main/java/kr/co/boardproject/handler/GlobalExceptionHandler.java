package kr.co.boardproject.handler;

import kr.co.boardproject.exception.ApiException;
import kr.co.boardproject.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> apiException(ApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode(), ex.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsException(ApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode(), ex.getErrorMessage());
        errorResponse.setStatus(400);
        errorResponse.setMessage("로그인정보가 일치하지 않습니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatusCode()));
    }
}
