package ba.unsa.etf.nwt.adopt_service.controller;

import ba.unsa.etf.nwt.adopt_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.adopt_service.response.EurekaResponse;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import ba.unsa.etf.nwt.adopt_service.service.AddPetRequestService;
import ba.unsa.etf.nwt.adopt_service.service.AdoptionRequestService;
import ba.unsa.etf.nwt.adopt_service.service.CommunicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class CommunicationsController {

    @Autowired
    private CommunicationsService communicationsService;

    @Autowired
    private AdoptionRequestService adoptionRequestService;

    @Autowired
    private AddPetRequestService addPetRequestService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/eureka/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/eureka/service-info/{applicationName}")
    public EurekaResponse serviceInfoByApplicationName(@PathVariable String applicationName) {
        List<ServiceInstance> instances = this.discoveryClient.getInstances(applicationName);
        EurekaResponse eurekaResponse = new EurekaResponse();
        for (ServiceInstance instance : instances) {
            eurekaResponse.setServiceId(instance.getServiceId());
            eurekaResponse.setIpAddress(instance.getHost());
            eurekaResponse.setPort(instance.getPort());
        }
        return eurekaResponse;
    }

    @GetMapping("/eureka/uri/{applicationName}")
    public String getURIfromService(@PathVariable String applicationName) {
        return communicationsService.getUri(applicationName);
    }

    /*@RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/requests/delete/ifExists/{id}")
    public ResponseMessage deleteRequestIfExists(@RequestHeader("Authorization") String token, @PathVariable Long id){
        adoptionRequestService.findAndDeleteAdoptionRequest(token, id);
        addPetRequestService.findAndDeleteAddPetRequest(token, id);

        return new ResponseMessage(true, HttpStatus.OK, "Request found and deleted!");
    }*/

}
