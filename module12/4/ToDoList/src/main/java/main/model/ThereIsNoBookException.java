package main.model;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(code = NOT_FOUND, reason = "There is no book")
public class ThereIsNoBookException extends RuntimeException {
    @ControllerAdvice
    public class ExceptionMapper {

        @ExceptionHandler({ThereIsNoBookException.class})
        public ResponseEntity<String> handleRunTimeException(ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }
}
