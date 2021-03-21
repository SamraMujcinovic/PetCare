package ba.unsa.etf.nwt.pet_category_service.responses;

import lombok.Data;

@Data
public class Response {

    private String message;
    private Integer statusCode;

    public Response(String message) {
        this.message = message;
    }

    public Response(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
