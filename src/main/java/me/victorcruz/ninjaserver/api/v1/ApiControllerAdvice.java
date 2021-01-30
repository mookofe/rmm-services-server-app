package me.victorcruz.ninjaserver.api.v1;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import me.victorcruz.ninjaserver.api.v1.responses.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * API V1 controller advice
 *
 * Central class for handling Domain Exceptions
 */
@ControllerAdvice
public class ApiControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> validationErrors = this.mapValidationExceptionToErrorList(ex);

        ErrorResponse response = new ErrorResponse(
                "Unprocessable Entity",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                validationErrors
        );

        this.logger.info(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private List<String> mapValidationExceptionToErrorList(MethodArgumentNotValidException validException) {
        return validException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::buildValidationError)
                .collect(Collectors.toList());
    }

    private String buildValidationError(FieldError fieldError) {
        return String.format("Attribute `%s`, %s", fieldError.getField(), fieldError.getDefaultMessage());
    }
}
