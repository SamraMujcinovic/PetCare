package ba.unsa.etf.nwt.pet_category_service.seeder;

import ba.unsa.etf.nwt.pet_category_service.model.Category;
import ba.unsa.etf.nwt.pet_category_service.model.Pet;
import ba.unsa.etf.nwt.pet_category_service.model.Rase;
import ba.unsa.etf.nwt.pet_category_service.service.CategoryService;
import ba.unsa.etf.nwt.pet_category_service.service.PetService;
import ba.unsa.etf.nwt.pet_category_service.service.RaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DatabaseSeeder {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PetService petService;

    @Autowired
    private RaseService raseService;

    @EventListener
    public void seed(ContextRefreshedEvent event){
        seed();
    }

    private void seed() {

        //kategorije

        Category c1 = createCategory("Dog", "Dogs are domesticated mammals, not natural wild animals. ");
        Category c2 = createCategory("Cat", "Cats, also called domestic cats (Felis catus), are small, carnivorous (meat-eating) mammals, of the family Felidae.");
        Category c3 = createCategory("Fish", "Fish (plural: fish) are an aquatic group of vertebrates which live in water and respire (get oxygen) with gills. They do not have limbs.");
        Category c4 = createCategory("Bird", "Birds can be wonderful companions for people who are single or live alone. Their chatter and antics can be quite amusing.");

        //rase

        Rase r1 = createRase("American bulldog", "The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.", c1);
        Rase r2 = createRase("Bichon Frise", "The Bichon Frise is a cheerful, small dog breed with a love of mischief and a lot of love to give.",c1);
        Rase r3 = createRase("British Shorthair", "The British Shorthair is an easygoing feline. She enjoys affection but isn’t needy and dislikes being carried.", c2);
        Rase r4 = createRase("Goldfish", "Another cold-water fish, goldfish belong to the carp family. Because they enjoy cool water temperatures, keep goldfish in a separate tank from warm water fish.", c3);
        Rase r5 = createRase("Budgerigar", "Enjoying popularity around the world, budgies (also known as parakeets) are some of the best pet birds for good reason.", c4);

        //ljubimci

        String firstPet = "";
        String secondPet = "";
        String thirdPet = "";
        String fourthPet = "";
        String fifthPet = "";

        //pronadji putanju slika za bazu
        try {
            File resource1 = new ClassPathResource("/pet-photos-for-seeder/american_bulldog.jpg").getFile();
            firstPet = resource1.getAbsolutePath();
        } catch (Exception e){
            System.out.println("ERROR + " + e.getMessage());
        }

        try {
            File resource2 = new ClassPathResource("/pet-photos-for-seeder/bichon_frise.jpg").getFile();
            secondPet = resource2.getAbsolutePath();
        } catch (Exception e){
            System.out.println("ERROR + " + e.getMessage());
        }

        try {
            File resource3 = new ClassPathResource("/pet-photos-for-seeder/british_shorthair.jpg").getFile();
            thirdPet = resource3.getAbsolutePath();
        } catch (Exception e){
            System.out.println("ERROR + " + e.getMessage());
        }

        try {
            File resource4 = new ClassPathResource("/pet-photos-for-seeder/goldfish.jpg").getFile();
            fourthPet = resource4.getAbsolutePath();
        } catch (Exception e){
            System.out.println("ERROR + " + e.getMessage());
        }

        try {
            File resource5 = new ClassPathResource("/pet-photos-for-seeder/budgerigar.jpg").getFile();
            fifthPet = resource5.getAbsolutePath();
        } catch (Exception e){
            System.out.println("ERROR + " + e.getMessage());
        }

        createPet("Rex", "Sarajevo", firstPet, "This American Bulldog may look tough, but in reality he is very spoiled, loves children and is very friendly with everyone. He is used to a big group of people and gets along with other animals very well.", 2, true, r1);
        createPet("Pupi", "Tuzla", secondPet, "This Bichon Frise is a big diva. He likes his haircuts and makeovers. He is always the center of attention and a great friend to a man.", 1, true, r2);
        createPet("Cicko", "Zenica", thirdPet, "This British Shothair is a bit older cat, but it will give you all the love it has. She is super lazy, and loves sleeping on the couch, especially if there is a human next to her.", 9, true, r3);
        createPet("Ribica", "Neum", fourthPet, "A goldfish is always a good addition to your aquarium. Maybe this one will grant you the famous 3 wishes... :D", 0, true, r4);
        createPet("Pricalica", "Brcko", fifthPet, "This Budgerigar loves hanging out in his cage, and in the high corner of a room. Sometimes, he will even say something, repeat something.", 1, true, r5);

    }

    private Category createCategory(String name, String description){
        Category c = new Category();
        c.setName(name);
        c.setDescription(description);
        categoryService.saveCategory(c);
        return c;
    }

    private Rase createRase(String name, String description, Category category){
        Rase r = new Rase();
        r.setName(name);
        r.setDescription(description);
        r.setCategory(category);
        raseService.saveRase(r);
        return r;
    }

    private void createPet(String name, String location, String image, String description, int age, boolean approved, Rase rase){
        Pet p = new Pet();
        p.setName(name);
        p.setLocation(location);
        p.setImage(image);
        p.setDescription(description);
        p.setAge(age);
        p.setApproved(approved);
        p.setRase(rase);
        petService.savePet(p);
        //p.setId(1L);
    }
}
