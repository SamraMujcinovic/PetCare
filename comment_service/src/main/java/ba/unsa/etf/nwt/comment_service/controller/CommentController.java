package ba.unsa.etf.nwt.comment_service.controller;

import ba.unsa.etf.nwt.comment_service.model.Comment;
import ba.unsa.etf.nwt.comment_service.response.ResponseMessage;
import ba.unsa.etf.nwt.comment_service.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comment")
    public List<Comment> getComments() {
        return commentService.getComment();
    }

    @PostMapping("/comment/{mainRoleId}")
    public ResponseMessage addComment(@Valid @RequestBody Comment comment, @PathVariable Long mainRoleId) {
            return commentService.addComment(comment, mainRoleId);
    }

    @GetMapping("/comment/{commentID}")
    public Comment getOneComment(@PathVariable Long commentID){
        return commentService.getOneComment(commentID);
    }

    @GetMapping("/comment/user/{userID}")
    public List<Comment> getUserComments(@PathVariable Long userID){
        return commentService.getUserComments(userID);
    }

    @GetMapping("/comment/category/{roleType}/{categoryID}")
    public List<Comment> getCategoryComment(@PathVariable Long roleType, @PathVariable Long categoryID){
        return commentService.getCategoryComment(roleType, categoryID);
    }

    @PutMapping("/comment/{commentID}")
    public ResponseMessage updateComment(@Valid @RequestBody Comment comment, @PathVariable Long commentID) {
        return commentService.updateComment(comment, commentID);
    }

    @DeleteMapping("/comment/{commentID}")
    public ResponseMessage deleteComment(@PathVariable Long commentID){
        return commentService.deleteComment(commentID);
    }
}

