package org.rentifytools.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class AppError {
    private String message;
    private HttpStatus status;
    private Date timestamp;

    public AppError(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
