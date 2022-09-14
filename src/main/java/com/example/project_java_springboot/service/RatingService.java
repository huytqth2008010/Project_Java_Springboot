package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.Customer;
import com.example.project_java_springboot.entity.Rating;
import com.example.project_java_springboot.entity.dto.RatingDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.repository.CustomerRepository;
import com.example.project_java_springboot.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> findById(Integer id) {
        return ratingRepository.findById(id);
    }

    public Rating save(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setRating_star(ratingDTO.getRating_star());
        rating.setId_product(ratingDTO.getId_product());
        rating.setId_account(ratingDTO.getId_account());
        rating.setStatus(EnumStatus.ACTIVE);
        return ratingRepository.save(rating);
    }
    public Rating update(int id, Rating objRequestUpdate) {
        Optional<Rating> obj = ratingRepository.findById(id);
        Rating objGet = obj.get();
        objGet.setRating_star(objRequestUpdate.getRating_star());
        objGet.setId_product(objRequestUpdate.getId_product());
        objGet.setId_account(objRequestUpdate.getId_account());
        objGet.setStatus(EnumStatus.ACTIVE);
        return ratingRepository.save(objGet);
    }
    public void deleteById(Integer id) {
        ratingRepository.deleteById(id);
    }
}
