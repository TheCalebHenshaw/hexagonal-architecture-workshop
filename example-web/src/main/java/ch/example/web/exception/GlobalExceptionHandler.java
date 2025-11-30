package ch.example.web.exception;

import ch.example.application.service.FraudDetectedException;
import ch.example.application.service.InsufficientFundsException;
import ch.example.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setError(ErrorResponse.ErrorEnum.BAD_REQUEST);
        error.setMessage(ex.getMessage());
        error.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setError(ErrorResponse.ErrorEnum.INSUFFICIENT_FUNDS);
        error.setMessage(ex.getMessage());
        error.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(error);
    }

    @ExceptionHandler(FraudDetectedException.class)
    public ResponseEntity<ErrorResponse> handleFraudDetectedException(FraudDetectedException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setError(ErrorResponse.ErrorEnum.FRAUD_DETECTED);
        error.setMessage(ex.getMessage());
        error.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        ErrorResponse error = new ErrorResponse();
        error.setError(ErrorResponse.ErrorEnum.BAD_REQUEST);
        error.setMessage(message);
        error.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setError(ErrorResponse.ErrorEnum.INTERNAL_ERROR);
        error.setMessage("An unexpected error occurred: " + ex.getMessage());
        error.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
