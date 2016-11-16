package pl.gda.pg.ds.sruv.handlers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.gda.pg.ds.sruv.exceptions.EntityAlreadyExistsException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private final static Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = NullPointerException.class)
    public String handleNullPointerExceptionException(NullPointerException e) {
        return "";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public String handleEntityAlreadyExistsException(Exception e) {
        LOG.info(e.getMessage());
        return "";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e) {
        LOG.info("Bad request; " + e.getMessage());
        return "";
    }
}
