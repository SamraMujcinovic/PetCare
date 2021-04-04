package ba.unsa.etf.nwt.pet_category_service.controller;

import ba.unsa.etf.nwt.pet_category_service.model.Pet;
import ba.unsa.etf.nwt.pet_category_service.model.Rase;
import ba.unsa.etf.nwt.pet_category_service.response.EurekaResponse;
import ba.unsa.etf.nwt.pet_category_service.service.PetService;
import ba.unsa.etf.nwt.pet_category_service.service.RaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class CommunicationsController {

    @Autowired
    private PetService petService;

    @Autowired
    private RaseService raseService;

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

    //vraca id trenutnog peta
    @GetMapping("/current/pet/petID/{id}")
    public Long getCurrentPetID(@NotNull @PathVariable Long id){
        Pet pet = petService.getPetById(id);
        return pet.getId();
    }


    //vraca id rase trenutnog peta
    @GetMapping("/current/pet/raseID/{id}")
    public Long getCurrentPetRaseID(@NotNull @PathVariable Long id){
        //argument je id peta trenutnog
        Pet pet = petService.getPetById(id);
        //vraceni id je id rase trenutnog peta
        return pet.getRase().getId();
    }

    //vraca id trenutne rase
    @GetMapping("/current/rase/raseID/{id}")
    public Long getCurrentRaseID(@NotNull @PathVariable Long id){
        Rase rase = raseService.getRaseById(id);
        return rase.getId();
    }
}
