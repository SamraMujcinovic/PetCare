package ba.unsa.etf.nwt.user_service.exception;

import ba.unsa.etf.nwt.user_service.response.ErrorResponse;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException e){
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());
        ErrorResponse er = new ErrorResponse(new ResponseMessage(false, HttpStatus.NOT_FOUND, "Exception was thrown"), details);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse(new ResponseMessage(false, HttpStatus.BAD_REQUEST,"Validation Failed"), details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}