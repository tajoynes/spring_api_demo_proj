package com.example.api.controllers;

import com.example.api.dto.ReviewDto;
import com.example.api.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("/pokemon/{pokemonId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int pokemonId, @RequestBody ReviewDto reviewDto){
        return new ResponseEntity<>(reviewService.createReview(pokemonId,reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews")
    public List<ReviewDto> getReviewByPokemonId(@PathVariable(value = "pokemonId")int pokemonId){
        return reviewService.getReviewsByPokemonId(pokemonId);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "pokemonId")int pokemonId, @PathVariable(value = "id") int reviewId){
        ReviewDto reviewDto = reviewService.getReviewById(reviewId, pokemonId);

        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/pokemon/{pokemonId}/reviews/{id}")
    public  ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "pokemonId")int pokemonId, @PathVariable(value = "id")int reviewId, @RequestBody ReviewDto reviewDto){
        ReviewDto updatedReviewDto = reviewService.updateReview(pokemonId,reviewId, reviewDto);
        return new ResponseEntity<>(updatedReviewDto, HttpStatus.OK);
    }

    @DeleteMapping("/pokemon/{pokemonId}/reviews/{id}")
    public  ResponseEntity<String> deletedReview(@PathVariable(value = "pokemonId")int pokemonId, @PathVariable(value = "id")int reviewId){
        reviewService.deleteReview(reviewId, pokemonId);

        return new ResponseEntity<>("Review was deleted", HttpStatus.OK);
    }


}
