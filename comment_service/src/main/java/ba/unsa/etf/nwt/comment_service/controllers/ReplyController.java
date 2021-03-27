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

    @GetMapping("/reply")
    public List<Reply> getReplies() {
        return replyService.getReply();
    }

    @PostMapping("/reply/{commentId}")
    public ResponseMessage addReply(@RequestBody Reply reply, @PathVariable Long commentId) {
          return replyService.addReply(reply, commentId);
    }

    @GetMapping("/reply/comment/{commentID}")
    public List<Reply> getOneNotification(@PathVariable Long commentID){
        return replyService.getAllReplyForComment(commentID);
    }

    @PutMapping("/reply/{replyID}")
    public ResponseMessage updateReply(@RequestBody Comment reply, @PathVariable Long replyID) {
        return replyService.updateReply(reply, replyID);
    }

    @DeleteMapping("/reply/{replyID}")
    public ResponseMessage deleteReply(@PathVariable Long replyID){
        return replyService.deleteReply(replyID);
    }
}
