package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

    private ReviewRepository reviewRepository;

    @Autowired
    ReviewRepositoryTests(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Test
    public void ReviewRepository_SaveAll_returnSavedReview(){

        Review review = Review.builder().title("title").content("content").stars(5).build();

        Review savedReview = reviewRepository.save(review);

        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_findAll_ReturnMoreThanOneReview(){

        Review review1 = Review.builder().title("title").content("content").stars(5).build();

        Review review2 = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> reviewList = reviewRepository.findAll();
        Assertions.assertThat(reviewList).isNotNull();
        Assertions.assertThat(reviewList.size()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_findById_returnReviewById(){

        Review review = Review.builder().title("title").content("content").stars(5).build();

        Review savedReview = reviewRepository.save(review);

        Review returnReview = reviewRepository.findById(savedReview.getId()).get();

        Assertions.assertThat(returnReview).isNotNull();
    }

    @Test
    public void ReviewRepository_UpdateReview_returnReview(){

        Review review = Review.builder().title("title").content("content").stars(5).build();

         reviewRepository.save(review);

        Review returnReview = reviewRepository.findById(review.getId()).get();
        returnReview.setContent("content1");
        returnReview.setStars(3);
        returnReview.setTitle("title1");

        Review updateReview = reviewRepository.save(returnReview);


        Assertions.assertThat(updateReview).isNotNull();
        Assertions.assertThat(updateReview.getId()).isGreaterThan(0);

    }


    @Test
    public void ReviewRepository_Delete_Review_returnIsEmptyReview(){

        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);
        reviewRepository.delete(review);

       Optional<Review> returnReview = reviewRepository.findById(review.getId());

        Assertions.assertThat(returnReview).isNotNull();
    }


}
