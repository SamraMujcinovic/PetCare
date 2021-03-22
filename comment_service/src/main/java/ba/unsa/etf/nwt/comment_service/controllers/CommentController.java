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
    private final MainRoleService mainRoleService;

    @GetMapping("/comment")
    public List<Comment> getComments() {
        return commentService.getComment();
    }

    @PostMapping("/comment/{mainRoleId}")
    public ResponseMessage addComment(@RequestBody Comment comment, @PathVariable Long mainRoleId) {

        if(mainRoleId != 1L && mainRoleId != 2L) return new ResponseMessage(false, "Invalid role ID!!", "BAD_REQUEST");

        if(comment.getTitle().equals("") && comment.getContent().equals("")) return new ResponseMessage(false, "Title and content can't be blank!!", "BAD_REQUEST");

        if(comment.getTitle().length() < 2 || comment.getTitle().length() > 1000) return new ResponseMessage(false, "Title must be between 2 and 1000 characters!!", "BAD_REQUEST");

        if(comment.getContent().length() < 2 || comment.getContent().length() > 1000) return new ResponseMessage(false, "Content must be between 2 and 1000 characters!!", "BAD_REQUEST");

        try {
            if(mainRoleId == 1L) {
                comment.setRoles(mainRoleService.getRoleByName(SectionRoleName.ROLE_CATEGORY));
            }
            else comment.setRoles(mainRoleService.getRoleByName(SectionRoleName.ROLE_PET));
            commentService.addComment(comment);
            return new ResponseMessage(true, "Comment added successfully!!", "OK");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, "Comment isn't added!!", "NOT_FOUND");
        }
    }

    @GetMapping("/comment/{commentID}")
    public Comment getOneComment(@PathVariable Long commentID){
        return commentService.getOneComment(commentID);
    }

    @GetMapping("/comment/user/{userID}")
    public List<Comment> getUserComments(@PathVariable Long userID){
        return commentService.getUserComments(userID);
    }
}

