package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.Slide;
import com.example.project_java_springboot.entity.dto.SlideDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.service.SlideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/slides")
public class SlideController {
    private final SlideService slideService;
    @Autowired
    private final ModelMapper modelMapper;

    public SlideController(SlideService slideService, ModelMapper modelMapper) {
        this.slideService = slideService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Slide>> findAll() {
        return ResponseEntity.ok(slideService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Slide> save(@RequestBody SlideDTO slideDTO) {
        slideDTO.setStatus(EnumStatus.ACTIVE.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(slideService.save(slideDTO));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<SlideDTO> update(@PathVariable Integer id, @RequestBody SlideDTO slideDTO) {
        Optional<Slide> optionalCategory = slideService.findById(id);

        if (!optionalCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Slide slide = new Slide();
        Slide objRequest = modelMapper.map(slideDTO, Slide.class);
        Slide obj = slideService.update(id, objRequest);
        SlideDTO objResponse = modelMapper.map(obj, SlideDTO.class);
        objResponse.setStatus(EnumStatus.ACTIVE.name());
        return ResponseEntity.ok().body(objResponse);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Slide> findById(@PathVariable Integer id) {
        Optional<Slide> optionalObj = slideService.findById(id);

        if (!optionalObj.isPresent()) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(optionalObj.get());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public boolean delete(@PathVariable Integer id) {
        slideService.deleteById(id);
        return true;
    }

}
