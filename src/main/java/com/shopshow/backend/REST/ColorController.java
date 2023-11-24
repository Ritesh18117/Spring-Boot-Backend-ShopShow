package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Color;
import com.shopshow.backend.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping("/test")
    public String test(){
        return "This is test from Color Controller";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Color>> getAllColor(){
        return colorService.getAllColor();
    }
    @PostMapping("/addColor")
    private ResponseEntity<Color> addColor(@RequestBody Color color){
        return colorService.addColor(color);
    }
}
