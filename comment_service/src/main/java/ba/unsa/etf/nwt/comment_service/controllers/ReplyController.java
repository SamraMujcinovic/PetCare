package ba.unsa.etf.nwt.comment_service.controllers;

import ba.unsa.etf.nwt.comment_service.models.Reply;
import ba.unsa.etf.nwt.comment_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.comment_service.services.ReplyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/reply")
    public List<Reply> getReplies() {
        return replyService.getReply();
    }

    @PostMapping("/reply")
    public ResponseMessage addReply(@RequestBody Reply reply) {

        if(reply.getContent().equals("") && reply.getUserID().equals(null) && reply.getComment().equals(null)) return new ResponseMessage(false, "Comment, content and user ID can't be blank!!", "BAD_REQUEST");

        if(reply.getContent().length() < 2 || reply.getContent().length() > 100) return new ResponseMessage(false, "Content must be between 2 and 100 characters!!", "BAD_REQUEST");

        if(reply.getContent().equals("")) return new ResponseMessage(false, "Content can't be blank!!", "BAD_REQUEST");

        //if(reply.getUserID().equals(null)) return new ResponseMessage(false, "User ID can't be blank!!", "BAD_REQUEST");

        //if(reply.getComment().equals(null)) return new ResponseMessage(false, "Comment can't be blank!!", "BAD_REQUEST");

        replyService.addReply(reply);

        return new ResponseMessage(true, "Reply added successfully!!", "OK");
    }

    @GetMapping("/reply/comment/{commentID}")
    public List<Reply> getOneNotification(@PathVariable Long commentID){
        return replyService.getAllReplyForComment(commentID);
    }
}
