package ba.unsa.etf.nwt.comment_service.controllers;

import ba.unsa.etf.nwt.comment_service.models.Reply;
import ba.unsa.etf.nwt.comment_service.services.ReplyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Reply addReply(@RequestBody Reply reply) {
        return replyService.addReply(reply);
    }
}
