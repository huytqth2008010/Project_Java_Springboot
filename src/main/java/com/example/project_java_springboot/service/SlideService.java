package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.Rating;
import com.example.project_java_springboot.entity.Reply;
import com.example.project_java_springboot.entity.Slide;
import com.example.project_java_springboot.entity.dto.SlideDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.repository.SlideRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SlideService {
    private final SlideRepository slideRepository;

    public SlideService(SlideRepository slideRepository) {
        this.slideRepository = slideRepository;
    }


    public List<Slide> findAll() {
        return slideRepository.findAll(Sort.by("id"));
    }

    public Optional<Slide> findById(Integer id) {
        return slideRepository.findById(id);
    }

    public Slide save(SlideDTO slideDTO) {
       Slide obj = new Slide();
       obj.setNote(slideDTO.getNote());
       obj.setThumbnail(slideDTO.getThumbnail());
       obj.setNote(slideDTO.getNote());
       obj.setStatus(EnumStatus.ACTIVE);
        return slideRepository.save(obj);
    }
    public Slide update(int id, Slide objRequestUpdate) {
        Optional<Slide> obj = slideRepository.findById(id);
        Slide objGet = obj.get();
        objGet.setThumbnail(objRequestUpdate.getThumbnail());
        objGet.setNote(objRequestUpdate.getNote());
        objGet.setStatus(EnumStatus.ACTIVE);
        return slideRepository.save(objGet);
    }
    public void deleteById(Integer id) {
        slideRepository.deleteById(id);
    }
}
