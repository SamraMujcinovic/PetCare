package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.exceptions.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.models.Category;
import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.repository.CategoryRepository;
import ba.unsa.etf.nwt.pet_category_service.responses.CategoryResponse;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public ResponseEntity<Category> addCategory(Category category) {
        if(category.getName().equals("")) return new ResponseEntity(new Response(false, "Category name can't be blank!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(category.getName().length()<2 || category.getName().length()>50) return new ResponseEntity(new Response(false, "Category name must be between 2 and 50 characters!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        categoryRepository.save(category);
        return new ResponseEntity(new Response(true, "Category added successfully!", "OK"), HttpStatus.OK);

    }

    public void saveCategory(Category c) {
        categoryRepository.save(c);
    }


    public CategoryResponse getCategory(Long categoryID) {
        try{
            Category category = getCategoryById(categoryID);
            return new CategoryResponse(category, "Request OK!", "OK", true);

        }catch (ResourceNotFoundException e){
            return new CategoryResponse(null, "No category with that ID!", "NOT FOUND", false);
        }

    }

    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No category with ID " + id));
    }

    public Response deleteCategory(Long id) {
        try {
            Category c = getCategoryById(id);
            categoryRepository.deleteById(id);
            return new Response(true, "Category successfully deleted!", "OK");
        }catch (ResourceNotFoundException e){
            return new Response(false, "Category with that ID not found!", "NOT FOUND");
        }
    }
}
