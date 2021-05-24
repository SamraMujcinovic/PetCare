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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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


    /*@PostMapping("/upload/photo")
    public String uploadPhoto(@RequestParam("file") MultipartFile multipartFile){

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        System.out.println("FILENAME: " + fileName);

        String uploadDir = "./pet-photos/";

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            try {
                Files.createDirectories(uploadPath);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
            System.out.println("FILEPATH " + filePath);
            return filePath.toFile().getAbsolutePath();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }*/

}
