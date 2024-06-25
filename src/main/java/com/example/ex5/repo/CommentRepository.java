package com.example.ex5.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for managing Comment entities.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Finds comments by the associated news ID.
     *
     * @param newsId the ID of the news
     * @return a list of comments associated with the news ID
     */
    List<Comment> findByNewsId(Long newsId);

    /**
     * Finds comments by the associated user.
     *
     * @param user the user who made the comments
     * @return a list of comments associated with the user
     */
    List<Comment> findByUser(User user);
}
