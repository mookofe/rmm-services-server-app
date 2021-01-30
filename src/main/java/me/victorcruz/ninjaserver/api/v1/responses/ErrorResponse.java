package me.victorcruz.ninjaserver.api.v1.responses;

import lombok.Getter;
import java.util.List;

/**
 * Error response
 *
 * This class serves as DTO to represent API Errors
 */
@Getter
public class ErrorResponse {
    private String error;
    private int status;
    private List<String> errors;

    public ErrorResponse(String error, int status){
        this.error = error;
        this.status = status;
    }

    public ErrorResponse(String error, int status, List<String> errors){
        this.error = error;
        this.status = status;
        this.errors = errors;
    }
}
