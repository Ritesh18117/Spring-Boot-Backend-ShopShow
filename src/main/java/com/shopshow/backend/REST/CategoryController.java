package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Category;
import com.shopshow.backend.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    private CategoryServices categoryServices;

    @GetMapping("/test")
    public String test(){
        return "Hello World from Category!!";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAllCategory(){
        return categoryServices.getAllCategory();
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        return categoryServices.addCategory(category);
    }
}
