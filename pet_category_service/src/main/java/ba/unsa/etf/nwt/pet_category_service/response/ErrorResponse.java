package ba.unsa.etf.nwt.pet_category_service.response;

import java.util.List;

public class ErrorResponse {
    private Response responseMessage;
    private List<String> details;

    public ErrorResponse(Response responseMessage, List<String> details) {
        this.responseMessage = responseMessage;
        this.details = details;
    }

    public Response getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(Response responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}