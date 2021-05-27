package ba.unsa.etf.nwt.adopt_service.seeder;

import ba.unsa.etf.nwt.adopt_service.model.AddPetRequest;
import ba.unsa.etf.nwt.adopt_service.model.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.repository.AddPetRequestRepository;
import ba.unsa.etf.nwt.adopt_service.repository.AdoptionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {

    @Autowired
    private AddPetRequestRepository addPetRequestRepository;

    @Autowired
    private AdoptionRequestRepository adoptionRequestRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedDatabase();
    }

    private void seedDatabase() {
        createAdoptionRequest(1L, 5L, "Hi! I'd like to adopt your wonderful pet!", false);
        createAdoptionRequest(2L, 5L, "Hi! Your pet looks it needs a new home, I'll be happy to help.", false);
        createAdoptionRequest(1L, 5L, "Hello. My Name is 1750, I need a new pet friend, and it looks like Garo could be it", false);
        createAdoptionRequest(3L, 5L, "Hi! I'd like to adopt your wonderful pet!", true);

        createAddPetRequest(5L, 1L, "I found this beautiful cat in front of my door. Could you give it a new home?", false);
        createAddPetRequest(4L, 2L, "I found this beautiful dog in front of my garage. Could you give it a new home?", true);
        createAddPetRequest(2L, 3L, "This is Lucky. He needs a new home. Is there any way to give him a loving family?", false);
        createAddPetRequest(1L, 4L, "Prazan string", true);
    }

    private void createAddPetRequest(Long userID, Long newPetID, String message, boolean approved) {
        AddPetRequest addPetRequest = new AddPetRequest();
        addPetRequest.setUserID(userID);
        addPetRequest.setNewPetID(newPetID);
        addPetRequest.setMessage(message);
        addPetRequest.setApproved(approved);
        addPetRequestRepository.save(addPetRequest);
    }

    private void createAdoptionRequest(Long userID, Long PetID, String message, boolean approved) {
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setUserID(userID);
        adoptionRequest.setPetID(PetID);
        adoptionRequest.setMessage(message);
        adoptionRequest.setApproved(approved);
        adoptionRequestRepository.save(adoptionRequest);
    }
}
