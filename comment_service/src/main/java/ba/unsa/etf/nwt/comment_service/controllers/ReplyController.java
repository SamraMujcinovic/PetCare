package ba.unsa.etf.nwt.comment_service.controllers;

import ba.unsa.etf.nwt.comment_service.models.Comment;
import ba.unsa.etf.nwt.comment_service.models.Reply;
import ba.unsa.etf.nwt.comment_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.comment_service.services.CommentService;
import ba.unsa.etf.nwt.comment_service.services.ReplyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;
    private final CommentService commentService;

    @GetMapping("/reply")
    public List<Reply> getReplies() {
        return replyService.getReply();
    }

    @PostMapping("/reply/{commentId}")
    public ResponseMessage addReply(@RequestBody Reply reply, @PathVariable Long commentId) {

        if(reply.getContent().equals("")) return new ResponseMessage(false, "Content can't be blank!!", "BAD_REQUEST");

        if(reply.getContent().length() < 2 || reply.getContent().length() > 1000) return new ResponseMessage(false, "Content must be between 2 and 1000 characters!!", "BAD_REQUEST");

        try {
            reply.setComment(new Comment(commentService.getOneComment(commentId)));
            replyService.addReply(reply);
            return new ResponseMessage(true, "Reply added successfully!!", "OK");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, "Reply isn't added!!", "NOT_FOUND");
        }
    }

    @GetMapping("/reply/comment/{commentID}")
    public List<Reply> getOneNotification(@PathVariable Long commentID){
        return replyService.getAllReplyForComment(commentID);
    }
}
