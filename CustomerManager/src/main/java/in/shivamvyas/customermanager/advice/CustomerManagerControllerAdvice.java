package in.shivamvyas.customermanager.advice;

import in.shivamvyas.customermanager.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomerManagerControllerAdvice {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCustomerNotFoundException(CustomerNotFoundException cnfe) {
        ErrorDetails error = new ErrorDetails(cnfe.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleOtherException(Exception e) {
        ErrorDetails error = new ErrorDetails(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
