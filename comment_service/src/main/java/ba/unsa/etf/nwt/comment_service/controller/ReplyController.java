package ba.unsa.etf.nwt.comment_service.controller;

import ba.unsa.etf.nwt.comment_service.model.Comment;
import ba.unsa.etf.nwt.comment_service.model.Reply;
import ba.unsa.etf.nwt.comment_service.response.ResponseMessage;
import ba.unsa.etf.nwt.comment_service.service.ReplyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseMessage addReply(@Valid  @RequestBody Reply reply, @PathVariable Long commentId) {
          return replyService.addReply(reply, commentId);
    }

    @GetMapping("/reply/comment/{commentID}")
    public List<Reply> getOneNotification(@PathVariable Long commentID){
        return replyService.getAllReplyForComment(commentID);
    }

    @PutMapping("/reply/{replyID}")
    public ResponseMessage updateReply(@Valid @RequestBody Reply reply, @PathVariable Long replyID) {
        return replyService.updateReply(reply, replyID);
    }

    @DeleteMapping("/reply/{replyID}")
    public ResponseMessage deleteReply(@PathVariable Long replyID){
        return replyService.deleteReply(replyID);
    }
}
