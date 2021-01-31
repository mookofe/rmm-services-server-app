package me.victorcruz.ninjaserver.api.v1;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import javax.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import me.victorcruz.ninjaserver.api.v1.responses.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import me.victorcruz.ninjaserver.domain.exceptions.DeviceNotFoundException;
import me.victorcruz.ninjaserver.domain.exceptions.NotCompatibleServiceException;
import me.victorcruz.ninjaserver.domain.exceptions.ServiceAlreadyExistForDeviceException;

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

    @ExceptionHandler(value = {DeviceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleDeviceNotFound(DeviceNotFoundException ex) {
        this.logger.info(ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        this.logger.info(ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {NotCompatibleServiceException.class})
    public ResponseEntity<ErrorResponse> handleNotCompatibleService(NotCompatibleServiceException ex) {
        this.logger.info(ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {ServiceAlreadyExistForDeviceException.class})
    public ResponseEntity<ErrorResponse> handleServiceExisting(ServiceAlreadyExistForDeviceException ex) {
        this.logger.info(ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
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

    private ResponseEntity<ErrorResponse> buildResponse(String message, HttpStatus status) {
        ErrorResponse response = new ErrorResponse(
                message,
                status.value()
        );

        return new ResponseEntity<>(response, status);
    }
}
