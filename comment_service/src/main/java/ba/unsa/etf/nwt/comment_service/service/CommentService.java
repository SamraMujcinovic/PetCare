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
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MainRoleService mainRoleService;

    public List<Comment> getComment() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            List<Comment> comments = commentRepository.findAll();
            for(Comment comment : comments){
                String username = restTemplate.getForObject("http://localhost:8080/user/" + comment.getUsername(), String.class);
                comment.setUsername(username);
                commentRepository.save(comment);
            }
            return comments;
        }
        catch (RuntimeException e){
            throw new ResourceNotFoundException ("Not found!!");
        }
    }

    public ResponseMessage addComment(Comment comment, Long mainRoleId) {

        RestTemplate restTemplate = new RestTemplate();

        if(mainRoleId != 1L && mainRoleId != 2L)  return new ResponseMessage(true, HttpStatus.OK,"Notification isn't added!!");
        try {
            if(mainRoleId == 1L) {
                comment.setRoles(mainRoleService.getRoleByName(SectionRoleName.ROLE_CATEGORY));
            }
            else comment.setRoles(mainRoleService.getRoleByName(SectionRoleName.ROLE_PET));
            String username = restTemplate.getForObject("http://localhost:8080/user/me/username", String.class);
            comment.setUsername(username);
            commentRepository.save(comment);
            return new ResponseMessage(true, HttpStatus.OK,"Comment added successfully!!");
        }
        catch (RuntimeException e){
            throw new WrongInputException("Comment isn't added!!");
        }
    }

    public List<Comment> getUserComments(String username) {
        return commentRepository
                .findAll()
                .stream()
                .filter(c -> c.getUsername().equals(username))
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

    public List<Comment> getCategoryComment(Long roleType, Long categoryID) {
        RestTemplate restTemplate = new RestTemplate();

        SectionRoleName roleName = SectionRoleName.ROLE_PET;
        if (roleType == 1L) roleName = SectionRoleName.ROLE_CATEGORY;

        SectionRoleName finalRoleName = roleName;
        List<Comment> comments = commentRepository
                .findAll()
                .stream()
                .filter(c -> c.getMainRole().getName().equals(finalRoleName) && c.getCategoryID().equals(categoryID))
                .collect(Collectors.toList());

        for(Comment comment : comments){
            String username = restTemplate.getForObject("http://localhost:8080/user/" + comment.getUsername(), String.class);
            comment.setUsername(username);
            if (roleName == SectionRoleName.ROLE_PET) {
                Long categoryId = restTemplate.getForObject("http://localhost:8084/current/pet/petID/" + comment.getCategoryID(), Long.class);
                comment.setCategoryID(categoryId);
            }
            else {
                Long categoryId = restTemplate.getForObject("http://localhost:8084/current/rase/raseID/" + comment.getCategoryID(), Long.class);
                comment.setCategoryID(categoryId);
            }
            commentRepository.save(comment);
        }
        return comments;
    }

    public Comment saveComment(Comment c){
        return commentRepository.save(c);
    }
}
