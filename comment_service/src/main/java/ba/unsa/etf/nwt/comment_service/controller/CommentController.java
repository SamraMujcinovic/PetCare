package ba.unsa.etf.nwt.comment_service.controller;

import ba.unsa.etf.nwt.comment_service.model.Comment;
import ba.unsa.etf.nwt.comment_service.response.ResponseMessage;
import ba.unsa.etf.nwt.comment_service.security.CurrentUser;
import ba.unsa.etf.nwt.comment_service.security.UserPrincipal;
import ba.unsa.etf.nwt.comment_service.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    //javna
    @GetMapping("/comment")
    public List<Comment> getComments() {
        return commentService.getComment();
    }

    //zastitcena
    @PostMapping("/comment/{mainRoleId}")
    public ResponseMessage addComment(@Valid @RequestBody Comment comment, @PathVariable Long mainRoleId, @CurrentUser UserPrincipal currentUser) {
            return commentService.addComment(comment, mainRoleId, currentUser);
    }

    //javna
    @GetMapping("/comment/{commentID}")
    public Comment getOneComment(@PathVariable Long commentID){
        return commentService.getOneComment(commentID);
    }

    //zasticena
    @GetMapping("/comment/user/{username}")
    public List<Comment> getUserComments(@PathVariable String username){
        return commentService.getUserComments(username);
    }

    //svi
    @GetMapping("/comment/category/{roleType}/{categoryID}")
    public List<Comment> getCategoryComment(@PathVariable Long roleType, @PathVariable Long categoryID){
        return commentService.getCategoryComment(roleType, categoryID);
    }

    //zast
    @PutMapping("/comment/{commentID}")
    public ResponseMessage updateComment(@Valid @RequestBody Comment comment, @PathVariable Long commentID) {
        return commentService.updateComment(comment, commentID);
    }

    //zast
    @DeleteMapping("/comment/{commentID}")
    public ResponseMessage deleteComment(@PathVariable Long commentID){
        return commentService.deleteComment(commentID);
    }
}

