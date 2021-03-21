package ba.unsa.etf.nwt.pet_category_service.controllers;

import ba.unsa.etf.nwt.pet_category_service.models.Category;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import ba.unsa.etf.nwt.pet_category_service.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/category")
    public ResponseEntity<?> addCategory( @RequestBody Category category){
        if(category.getName().equals("")) return new ResponseEntity(new Response(false, "Category name can't be blank!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(category.getName().length()<2 || category.getName().length()>50) return new ResponseEntity(new Response(false, "Category name must be between 2 and 50 characters!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        categoryService.addCategory(category);
        return new ResponseEntity(new Response(true, "Category added successfully!", "OK"), HttpStatus.OK);
    }


}
