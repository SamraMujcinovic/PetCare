package ba.unsa.etf.nwt.notification_service.controller;

import ba.unsa.etf.nwt.notification_service.response.EurekaResponse;
import ba.unsa.etf.nwt.notification_service.response.ResponseMessage;
import ba.unsa.etf.nwt.notification_service.service.CommunicationsService;
import ba.unsa.etf.nwt.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommunicationsController {

    @Autowired
    private CommunicationsService communicationsService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private NotificationService notificationService;

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

    //rute za druge servise

    @GetMapping("/notifications/public/add/{userID}")
    public ResponseMessage addRegistrationNotification(@PathVariable(value = "userID") Long userID){

        //notifikacija za novog registrovanog usera
        if(userID != -1){
            return notificationService.addNotificationForRegistrationAndContactUs(userID, "There is a new registered user, check the list of users!");
        }
        //notifikacija za contact us formu
        else {
            return notificationService.addNotificationForRegistrationAndContactUs(userID, "Someone filled contact us form, check email!");
        }
    }

}
