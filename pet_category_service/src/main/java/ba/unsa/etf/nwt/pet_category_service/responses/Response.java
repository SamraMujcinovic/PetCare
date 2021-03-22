package ba.unsa.etf.nwt.pet_category_service.responses;

import lombok.Data;

@Data
public class Response {
    private Boolean success;
    private String message;
    private String status;

    public Response(Boolean success, String message, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}