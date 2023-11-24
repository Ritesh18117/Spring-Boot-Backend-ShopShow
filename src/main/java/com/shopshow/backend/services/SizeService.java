package com.shopshow.backend.services;

import com.shopshow.backend.dao.SizeRepository;
import com.shopshow.backend.entities.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    public ResponseEntity<List<Size>> getAllSize(){
        try{
            List<Size> sizes = (List<Size>) sizeRepository.findAll();
            if(sizes.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(sizes));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Size> addSize(@RequestBody Size size){
        try{
            sizeRepository.save(size);
            return ResponseEntity.of(Optional.of(size));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
