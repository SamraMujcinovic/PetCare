package ba.unsa.etf.nwt.comment_service.response;

public class EurekaResponse {
    private String serviceId;
    private String ipAddress;
    private int port;

    public EurekaResponse() {
        this.serviceId = "";
        this.ipAddress = "";
        this.port = 0;
    }

    public EurekaResponse(String serviceId, String ipAddress, int port) {
        this.serviceId = serviceId;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
