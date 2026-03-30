package SFWE405.project.code;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        ex.printStackTrace(); // prints full stack trace to console
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

@ExceptionHandler(OccupancyMetException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String OccupancyMetExceptionHandler(OccupancyMetException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String IllegalArgumentExceptionHandler(IllegalArgumentException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String IllegalStateExceptionHandler(IllegalStateException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(InsufficientInfoException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String InsufficientInfoExceptionHandler(InsufficientInfoException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(TimeSlotTakenException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String TimeSlotTakenExceptionHandler(TimeSlotTakenException ex) {
    return ex.getMessage();
  }
}
