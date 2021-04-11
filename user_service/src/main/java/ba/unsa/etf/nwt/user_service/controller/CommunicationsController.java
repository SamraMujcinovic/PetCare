package ba.unsa.etf.nwt.user_service.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.user_service.model.User;
import ba.unsa.etf.nwt.user_service.model.roles.Role;
import ba.unsa.etf.nwt.user_service.response.EurekaResponse;
import ba.unsa.etf.nwt.user_service.service.CommunicationsService;
import ba.unsa.etf.nwt.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunicationsController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommunicationsService communicationsService;

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

    //POPRAVITI NAKON AUTORIZACIJE
    //vrati username za dodavanje komentara
    @GetMapping("/user/me/username")
    public String getCurrentUsersUsername(/*@CurrentUser UserPrincipal currentUser*/){
        User user = new User();
        user.setUsername("alakovic1");
        return user.getUsername();
    }

    @GetMapping("/user/{username}")
    public String getUsersUsernameByUsername(@PathVariable(value = "username") String username) {
        try {
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
            return user.getUsername();
        }
        catch(ResourceNotFoundException e){
            return "UNKNOWN";
        }
    }

    //POPRAVITI NAKON AUTORIZACIJE
    @GetMapping("/user/me/id")
    public Long getCurrentUsersId(/*@CurrentUser UserPrincipal currentUser*/){
        User user = new User();
        user.setId(1L);
        return user.getId();
    }

    //vrati rolu trenutnog usera
    //POPRAVITI NAKON AUTORIZACIJE
    @GetMapping("/user/me/role")
    public List<String> getCurrentRole(/*@CurrentUser UserPrincipal currentUser*/){
        try {
            User user = userService.findByUsername("alakovic1")
                    .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

            List<String> roles = new ArrayList<>();

            for (Role role : user.getRoles()) {
                roles.add(role.getName().toString());
            }

            return roles;
        }
        catch(ResourceNotFoundException e){
            return new ArrayList<>();
        }
    }
}
