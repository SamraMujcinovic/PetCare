package ba.unsa.etf.nwt.comment_service.services;


import ba.unsa.etf.nwt.comment_service.models.Comment;
import ba.unsa.etf.nwt.comment_service.models.Reply;
import ba.unsa.etf.nwt.comment_service.repository.ReplyRepository;
import ba.unsa.etf.nwt.comment_service.responses.ResponseMessage;
import lombok.AllArgsConstructor;
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

        if(reply.getContent().equals("")) return new ResponseMessage(false, "Content can't be blank!!", "BAD_REQUEST");

        if(reply.getContent().length() < 2 || reply.getContent().length() > 1000) return new ResponseMessage(false, "Content must be between 2 and 1000 characters!!", "BAD_REQUEST");

        try {
            reply.setComment(new Comment(commentService.getOneComment(commentId)));
            replyRepository.save(reply);
            return new ResponseMessage(true, "Reply added successfully!!", "OK");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, "Reply isn't added!!", "NOT_FOUND");
        }
    }

    public List<Reply> getAllReplyForComment(Long commentID) {
        return replyRepository
                .findAll()
                .stream()
                .filter(r -> r.getComment().getId().equals(commentID))
                .collect(Collectors.toList());
    }

    public ResponseMessage updateReply(Comment reply, Long replyID) {
        if(reply.getContent().equals("")) return new ResponseMessage(false, "Content can't be blank!!", "BAD_REQUEST");

        if(reply.getContent().length() < 2 || reply.getContent().length() > 1000) return new ResponseMessage(false, "Content must be between 2 and 1000 characters!!", "BAD_REQUEST");

        try {
            Reply oldReply = replyRepository
                    .findAll()
                    .stream()
                    .filter(c -> c.getId().equals(replyID))
                    .collect(Collectors.toList()).get(0);
            oldReply.setContent(reply.getContent());
            replyRepository.save(oldReply);
            return new ResponseMessage(true, "Reply updated successfully!!", "OK");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, "Reply isn't updated!!", "NOT_FOUND");
        }
    }

    public ResponseMessage deleteReply(Long replyID) {
        try {
            replyRepository.deleteById(replyID);
            return new ResponseMessage(true, "Reply deleted successfully!!", "OK");
        } catch (Exception e) {
            return new ResponseMessage(false, "Reply isn't deleted!!", "NOT_FOUND");
        }
    }
}
