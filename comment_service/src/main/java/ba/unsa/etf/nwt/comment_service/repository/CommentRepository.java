package ba.unsa.etf.nwt.comment_service.repository;

import ba.unsa.etf.nwt.comment_service.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository <Comment, Long> {
}