package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Size;
import com.shopshow.backend.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping("/test")
    public String test(){
        return "This is the test from Size controller";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Size>> getAllSize(){
        return sizeService.getAllSize();
    }
    @PostMapping("/addSize")
    public ResponseEntity<Size> addSize(@RequestBody Size size){
        return sizeService.addSize(size);
    }
}
