package com.example.api.services.impl;

import com.example.api.dto.ReviewDto;
import com.example.api.models.Pokemon;
import com.example.api.models.Review;
import com.example.api.repository.PokemonRepository;
import com.example.api.repository.ReviewRepository;
import com.example.api.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private PokemonRepository pokemonRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow();
        review.setPokemon(pokemon);

        Review newReview = reviewRepository.save(review);

        return mapReviewToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int pokemonId) {
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);

        return reviews.stream().map(r -> mapReviewToDto(r)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        if (review.getPokemon().getId() != pokemon.getId()) {
             throw new RuntimeException("Values does not match");
        }

        return mapReviewToDto(review);
    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new RuntimeException("Values does not match");
        }
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updateReview = reviewRepository.save(review);

        return mapReviewToDto(updateReview);

    }

    @Override
    public void deleteReview(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new RuntimeException("Values does not match");
        }
        reviewRepository.delete(review);
    }


    private ReviewDto mapReviewToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());

       return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getTitle());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
