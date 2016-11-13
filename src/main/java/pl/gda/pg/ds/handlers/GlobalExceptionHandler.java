package pl.gda.pg.ds.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.gda.pg.ds.exceptions.EntityAlreadyExistsException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = NullPointerException.class)
    public String handleNullPointerExceptionException(NullPointerException e) {
        return "";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public String handleEntityAlreadyExistsException(Exception e) {
        return "";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e) {
        return "";
    }
}
