package ba.unsa.etf.nwt.pet_category_service.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response {
    private Boolean success;
    private String message;
    private HttpStatus status;

    public Response(Boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public Response(Response r){
        this.success = r.success;
        this.message = r.message;
        this.status = r.status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}