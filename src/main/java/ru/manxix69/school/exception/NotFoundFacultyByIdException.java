package ru.manxix69.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundFacultyByIdException extends RuntimeException {
    public NotFoundFacultyByIdException(String message) {
        super(message);
    }
}
