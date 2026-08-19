package be.ucll.robinghys.integrationproject.general.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                ErrorResponse error = new ErrorResponse(
                                status.value(),
                                status.name(),
                                ex.getMessage());
                return new ResponseEntity<>(error, status);
        }
}
