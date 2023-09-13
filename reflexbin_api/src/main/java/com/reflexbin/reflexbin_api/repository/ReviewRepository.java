package com.reflexbin.reflexbin_api.repository;

import com.reflexbin.reflexbin_api.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository class for Review
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * Method for find review by reviewId
     *
     * @param id
     * @return
     */
    Optional<Review> findById(Long id);
}
