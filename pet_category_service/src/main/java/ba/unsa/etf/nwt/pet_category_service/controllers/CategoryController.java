package ba.unsa.etf.nwt.pet_category_service.controllers;

import ba.unsa.etf.nwt.pet_category_service.models.Category;
import ba.unsa.etf.nwt.pet_category_service.responses.CategoryResponse;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import ba.unsa.etf.nwt.pet_category_service.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/categories")
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }


    @GetMapping("/category")
    public CategoryResponse getCategory(@RequestParam Long id){
        return categoryService.getCategory(id);
    }

    @GetMapping("/category/byName")
    public CategoryResponse getCategoryByName(@RequestParam String name){
        return categoryService.getCategoryByName(name);
    }

    @PostMapping("/category")
    public ResponseEntity<?> addCategory( @RequestBody Category category){

        return categoryService.addCategory(category);
    }

    @DeleteMapping("/category")
    public Response deleteCategory(@RequestParam Long id){
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/category/update")
    public CategoryResponse updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }

}
