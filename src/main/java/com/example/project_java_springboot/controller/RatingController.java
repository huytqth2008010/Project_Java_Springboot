package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.Customer;
import com.example.project_java_springboot.entity.Rating;
import com.example.project_java_springboot.entity.dto.CustomerDTO;
import com.example.project_java_springboot.entity.dto.RatingDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.service.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/ratings")
public class RatingController {
    private final RatingService ratingService;
    @Autowired
    private final ModelMapper modelMapper;

    public RatingController(RatingService ratingService, ModelMapper modelMapper) {
        this.ratingService = ratingService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Rating>> findAll() {
        return ResponseEntity.ok(ratingService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Rating> save(@RequestBody RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setStatus(EnumStatus.ACTIVE);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.save(ratingDTO));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<RatingDTO> update(@PathVariable Integer id, @RequestBody RatingDTO ratingDTO) {
        Optional<Rating> optionalCategory = ratingService.findById(id);
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Rating objRequest = modelMapper.map(ratingDTO, Rating.class);
        Rating obj = ratingService.update(id, objRequest);
        RatingDTO objResponse = modelMapper.map(obj, RatingDTO.class);
        objResponse.setStatus(EnumStatus.ACTIVE.name());
        return ResponseEntity.ok().body(objResponse);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Rating> findById(@PathVariable Integer id) {
        Optional<Rating> optionalObj = ratingService.findById(id);
        if (!optionalObj.isPresent()) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.ok(optionalObj.get());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public boolean delete(@PathVariable Integer id) {
        ratingService.deleteById(id);
        return true;
    }

}
