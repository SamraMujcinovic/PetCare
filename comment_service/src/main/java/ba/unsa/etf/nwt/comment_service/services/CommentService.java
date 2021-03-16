package ba.unsa.etf.nwt.comment_service.services;

import ba.unsa.etf.nwt.comment_service.models.Comment;

import ba.unsa.etf.nwt.comment_service.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getComment() {
        return commentRepository.findAll();
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

}
