package ba.unsa.etf.nwt.comment_service.service;


import ba.unsa.etf.nwt.comment_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.comment_service.exception.WrongInputException;
import ba.unsa.etf.nwt.comment_service.model.Comment;
import ba.unsa.etf.nwt.comment_service.model.Reply;
import ba.unsa.etf.nwt.comment_service.repository.ReplyRepository;
import ba.unsa.etf.nwt.comment_service.response.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentService commentService;

    public List<Reply> getReply() {
        return replyRepository.findAll();
    }

    public ResponseMessage addReply(Reply reply, Long commentId) {
        try {
            reply.setComment(new Comment(commentService.getOneComment(commentId)));
            replyRepository.save(reply);
            return new ResponseMessage(true, HttpStatus.OK,"Reply added successfully!!");
        }
        catch (RuntimeException e){
            throw new WrongInputException("Reply isn't added!!");
        }
    }

    public List<Reply> getAllReplyForComment(Long commentID) {
        return replyRepository
                .findAll()
                .stream()
                .filter(r -> r.getComment().getId().equals(commentID))
                .collect(Collectors.toList());
    }

    public ResponseMessage updateReply(Reply reply, Long replyID) {
        try {
            Reply oldReply = replyRepository
                    .findAll()
                    .stream()
                    .filter(c -> c.getId().equals(replyID))
                    .collect(Collectors.toList()).get(0);
            oldReply.setContent(reply.getContent());
            replyRepository.save(oldReply);
            return new ResponseMessage(true, HttpStatus.OK, "Reply updated successfully!!");
        }
        catch (RuntimeException e){
            throw new WrongInputException("Reply isn't updated!!");
        }
    }

    public ResponseMessage deleteReply(Long replyID) {
        try {
            replyRepository.deleteById(replyID);
            return new ResponseMessage(true, HttpStatus.OK,"Reply deleted successfully!!");
        } catch (Exception e) {
            throw  new ResourceNotFoundException( "Reply isn't deleted!!");
        }
    }
}
