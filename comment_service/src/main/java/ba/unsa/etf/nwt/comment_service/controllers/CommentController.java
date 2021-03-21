package ba.unsa.etf.nwt.comment_service.controllers;

import ba.unsa.etf.nwt.comment_service.models.Comment;
import ba.unsa.etf.nwt.comment_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.comment_service.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comment")
    public List<Comment> getComments() {
        return commentService.getComment();
    }

    @PostMapping("/comment")
    public ResponseMessage addComment(@RequestBody Comment comment) {

        if(comment.getTitle().equals("") && comment.getRoles().equals(null) && comment.getContent().equals("") && comment.getUserID().equals(null)) return new ResponseMessage(false, "Title, role, content and user ID can't be blank!!", "BAD_REQUEST");

        if(comment.getTitle().length() < 2 || comment.getTitle().length() > 100) return new ResponseMessage(false, "Title must be between 2 and 100 characters!!", "BAD_REQUEST");

        if(comment.getContent().length() < 2 || comment.getContent().length() > 100) return new ResponseMessage(false, "Title must be between 2 and 100 characters!!", "BAD_REQUEST");

        if(comment.getContent().equals("")) return new ResponseMessage(false, "Content can't be blank!!", "BAD_REQUEST");

        if(comment.getTitle().equals("")) return new ResponseMessage(false, "Title can't be blank!!", "BAD_REQUEST");

        if(comment.getUserID().equals(null)) return new ResponseMessage(false, "User ID can't be blank!!", "BAD_REQUEST");

        if(comment.getRoles().equals(null)) return new ResponseMessage(false, "Role can't be blank!!", "BAD_REQUEST");

        commentService.addComment(comment);

        return new ResponseMessage(true, "Comment added successfully!!", "OK");
    }

    @GetMapping("/comment/{commentID}")
    public Optional<Comment> getOneComment(@PathVariable Long commentID){
        return commentService.findById(commentID);
    }

    @GetMapping("/comment/user/{userID}")
    public List<Comment> getUserComments(@PathVariable Long userID){
        return commentService.getUserComments(userID);
    }
}

