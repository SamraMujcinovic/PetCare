package ba.unsa.etf.nwt.user_service.responses;

public class AvailabilityResponse {
    private Boolean available;

    public AvailabilityResponse(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
