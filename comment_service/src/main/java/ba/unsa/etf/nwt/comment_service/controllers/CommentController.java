package ba.unsa.etf.nwt.comment_service.controllers;

import ba.unsa.etf.nwt.comment_service.models.Comment;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.MainRole;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.SectionRoleName;
import ba.unsa.etf.nwt.comment_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.comment_service.services.CommentService;
import ba.unsa.etf.nwt.comment_service.services.MainRoleService;
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

    @PostMapping("/comment/{mainRoleId}")
    public ResponseMessage addComment(@RequestBody Comment comment, @PathVariable Long mainRoleId) {
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

    @PutMapping("/comment/{commentID}")
    public ResponseMessage updateComment(@RequestBody Comment comment, @PathVariable Long commentID) {
        return commentService.updateComment(comment, commentID);
    }

    @DeleteMapping("/comment/{commentID}")
    public ResponseMessage deleteComment(@PathVariable Long commentID){
        return commentService.deleteComment(commentID);
    }
}

