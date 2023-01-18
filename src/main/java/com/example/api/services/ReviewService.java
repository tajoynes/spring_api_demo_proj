package com.example.api.services;

import com.example.api.dto.ReviewDto;
import com.example.api.models.Review;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(int pokemonId,ReviewDto reviewDto);
    List<ReviewDto> getReviewsByPokemonId(int pokemonId);

    ReviewDto getReviewById(int reviewId,int pokemonId );
    ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto);

    void deleteReview(int reviewId, int pokemonId);
}
