package ba.unsa.etf.nwt.comment_service.services;

import ba.unsa.etf.nwt.comment_service.models.Comment;

import ba.unsa.etf.nwt.comment_service.models.Reply;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.SectionRoleName;
import ba.unsa.etf.nwt.comment_service.repository.CommentRepository;
import ba.unsa.etf.nwt.comment_service.responses.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MainRoleService mainRoleService;

    public List<Comment> getComment() {
        return commentRepository.findAll();
    }

    public ResponseMessage addComment(Comment comment, Long mainRoleId) {

        if(mainRoleId != 1L && mainRoleId != 2L) return new ResponseMessage(false, "Invalid role ID!!", "BAD_REQUEST");

        if(comment.getTitle().equals("") && comment.getContent().equals("")) return new ResponseMessage(false, "Title and content can't be blank!!", "BAD_REQUEST");

        if(comment.getTitle().length() < 2 || comment.getTitle().length() > 1000) return new ResponseMessage(false, "Title must be between 2 and 1000 characters!!", "BAD_REQUEST");

        if(comment.getContent().length() < 2 || comment.getContent().length() > 1000) return new ResponseMessage(false, "Content must be between 2 and 1000 characters!!", "BAD_REQUEST");

        try {
            if(mainRoleId == 1L) {
                comment.setRoles(mainRoleService.getRoleByName(SectionRoleName.ROLE_CATEGORY));
            }
            else comment.setRoles(mainRoleService.getRoleByName(SectionRoleName.ROLE_PET));
            commentRepository.save(comment);
            return new ResponseMessage(true, "Comment added successfully!!", "OK");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, "Comment isn't added!!", "NOT_FOUND");
        }
    }

    public List<Comment> getUserComments(Long userID) {
        return commentRepository
                .findAll()
                .stream()
                .filter(c -> c.getUserID().equals(userID))
                .collect(Collectors.toList());
    }

    public Comment getOneComment(Long commentID) {
        return commentRepository
                .findAll()
                .stream()
                .filter(c -> c.getId().equals(commentID))
                .collect(Collectors.toList()).get(0);
    }

    public ResponseMessage updateComment(Comment comment, Long commentID) {
        if(comment.getTitle().equals("") && comment.getContent().equals("")) return new ResponseMessage(false, "Title and content can't be blank!!", "BAD_REQUEST");

        if(comment.getTitle().length() < 2 || comment.getTitle().length() > 1000) return new ResponseMessage(false, "Title must be between 2 and 1000 characters!!", "BAD_REQUEST");

        if(comment.getContent().length() < 2 || comment.getContent().length() > 1000) return new ResponseMessage(false, "Content must be between 2 and 1000 characters!!", "BAD_REQUEST");

        try {
            Comment oldComment = commentRepository
                    .findAll()
                    .stream()
                    .filter(c -> c.getId().equals(commentID))
                    .collect(Collectors.toList()).get(0);
            oldComment.setTitle(comment.getTitle());
            oldComment.setContent(comment.getContent());
            commentRepository.save(oldComment);
            return new ResponseMessage(true, "Comment updated successfully!!", "OK");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, "Comment isn't updated!!", "NOT_FOUND");
        }
    }

    public ResponseMessage deleteComment(Long commentID) {
        try {
            commentRepository.deleteById(commentID);
            return new ResponseMessage(true, "Comment deleted successfully!!", "OK");
        } catch (Exception e) {
            return new ResponseMessage(false, "Comment isn't deleted!!", "NOT_FOUND");
        }
    }
}
