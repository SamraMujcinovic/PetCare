package ba.unsa.etf.nwt.user_service.exception;

import ba.unsa.etf.nwt.user_service.response.ErrorResponse;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDenied(HttpServletRequest request, AccessDeniedException ex) {

        List<String> details = new ArrayList<>();
        details.add("User with this role is not authorized to access this route!");

        ErrorResponse er = new ErrorResponse(new ResponseMessage(false, HttpStatus.FORBIDDEN, "Unauthorized request!"), details);
        return new ResponseEntity<>(er, HttpStatus.FORBIDDEN);
    }

}
