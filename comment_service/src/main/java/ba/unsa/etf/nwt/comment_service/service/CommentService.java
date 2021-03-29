package ba.unsa.etf.nwt.comment_service.service;

import ba.unsa.etf.nwt.comment_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.comment_service.exception.WrongInputException;
import ba.unsa.etf.nwt.comment_service.model.Comment;

import ba.unsa.etf.nwt.comment_service.model.sectionRole.SectionRoleName;
import ba.unsa.etf.nwt.comment_service.repository.CommentRepository;
import ba.unsa.etf.nwt.comment_service.response.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if(mainRoleId != 1L && mainRoleId != 2L)  return new ResponseMessage(true, HttpStatus.OK,"Notification isn't added!!");
        try {
            if(mainRoleId == 1L) {
                comment.setRoles(mainRoleService.getRoleByName(SectionRoleName.ROLE_CATEGORY));
            }
            else comment.setRoles(mainRoleService.getRoleByName(SectionRoleName.ROLE_PET));
            commentRepository.save(comment);
            return new ResponseMessage(true, HttpStatus.OK,"Comment added successfully!!");
        }
        catch (RuntimeException e){
            throw new WrongInputException("Notification isn't added!!");
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
        try {
            Comment oldComment = commentRepository
                    .findAll()
                    .stream()
                    .filter(c -> c.getId().equals(commentID))
                    .collect(Collectors.toList()).get(0);
            oldComment.setTitle(comment.getTitle());
            oldComment.setContent(comment.getContent());
            commentRepository.save(oldComment);
            return new ResponseMessage(true, HttpStatus.OK ,"Comment updated successfully!!");
        }
        catch (RuntimeException e){
            throw new WrongInputException("Comment isn't updated!!");
        }
    }

    public ResponseMessage deleteComment(Long commentID) {
        try {
            commentRepository.deleteById(commentID);
            return new ResponseMessage(true, HttpStatus.OK,"Comment deleted successfully!!");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Comment isn't deleted!!");
        }
    }
}
