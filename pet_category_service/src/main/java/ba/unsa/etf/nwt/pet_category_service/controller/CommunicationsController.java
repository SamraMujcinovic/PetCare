package ba.unsa.etf.nwt.pet_category_service.controller;

import ba.unsa.etf.nwt.pet_category_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.exception.WrongInputException;
import ba.unsa.etf.nwt.pet_category_service.model.Pet;
import ba.unsa.etf.nwt.pet_category_service.model.Rase;
import ba.unsa.etf.nwt.pet_category_service.request.PetRequest;
import ba.unsa.etf.nwt.pet_category_service.response.EurekaResponse;
import ba.unsa.etf.nwt.pet_category_service.service.CommunicationsService;
import ba.unsa.etf.nwt.pet_category_service.service.PetService;
import ba.unsa.etf.nwt.pet_category_service.service.RaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class CommunicationsController {

    @Autowired
    private PetService petService;

    @Autowired
    private RaseService raseService;

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

    @GetMapping("/api/auth/accessDenied")
    public void accessDenied(){
        throw new ResourceNotFoundException("User with this role is not authorized to access this route!");
    }

    //vraca id trenutnog peta
    @GetMapping("/current/pet/petID/{id}")
    public Long getCurrentPetID(@NotNull @PathVariable Long id){
        Pet pet = new Pet();
        try {
            pet = petService.getPetById(id);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
        return pet.getId();
    }


    //vraca id rase trenutnog peta
    @GetMapping("/current/pet/raseID/{id}")
    public Long getCurrentPetRaseID(@NotNull @PathVariable Long id){
        Pet pet = new Pet();
        try {
            //argument je id peta trenutnog
            pet = petService.getPetById(id);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
        //vraceni id je id rase trenutnog peta
        return pet.getRase().getId();
    }

    //vraca id trenutne rase
    @GetMapping("/current/rase/raseID/{id}")
    public Long getCurrentRaseID(@NotNull @PathVariable Long id){
        Rase rase = new Rase();
        try {
            rase = raseService.getRaseById(id);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
        return rase.getId();
    }

    @PostMapping("/petID/forAdopt")
    public Long addPetForAdopt(@Valid @RequestBody PetRequest petRequest){
        Long petID;
        try{
            petID = petService.addPetForAdopt(petRequest);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
        catch (WrongInputException ee){
            throw new WrongInputException(ee.getMessage());
        }
        return petID;
    }

}
