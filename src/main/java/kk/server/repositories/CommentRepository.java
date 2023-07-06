package kk.server.repositories;

import kk.server.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    public Comment findByUser(String login);
    public Comment findByPostId(Long id);
}
