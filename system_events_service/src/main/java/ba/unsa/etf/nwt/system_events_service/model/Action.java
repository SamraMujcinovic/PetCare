package ba.unsa.etf.nwt.system_events_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.protobuf.Timestamp;
import com.sun.istack.NotNull;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="actions")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(timezone="Europe/Sarajevo")
    @Column(name = "timestamp", nullable = true, updatable = false)
    private Timestamp timestamp;

    @NotNull
    @NotBlank
    private String microservice;

    //dodati kasnije
    //private String user;

    @NotNull
    @NotBlank
    private String actionType;

    @NotNull
    @NotBlank
    private String resourceName;

    @NotNull
    @NotBlank
    private String responseType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMicroservice() {
        return microservice;
    }

    public void setMicroservice(String microservice) {
        this.microservice = microservice;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
