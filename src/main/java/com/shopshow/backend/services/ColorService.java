package com.shopshow.backend.services;

import com.shopshow.backend.dao.ColorRepository;
import com.shopshow.backend.entities.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public ResponseEntity<List<Color>> getAllColor(){
        try{
            List<Color> colors = (List<Color>) colorRepository.findAll();
            if(colors.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(colors));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Color> addColor(@RequestBody Color color){
        try{
            colorRepository.save(color);
            return ResponseEntity.of(Optional.of(color));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
