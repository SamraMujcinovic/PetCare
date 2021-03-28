package ba.unsa.etf.nwt.user_service.response;

public class ResponseMessage {
    private Boolean success;
    private String message;
    private String status;

    public ResponseMessage(Boolean success, String message, String status) {
        this.success = success;
        this.message = message;
        this.status = status;
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
