package ba.unsa.etf.nwt.comment_service.services;


import ba.unsa.etf.nwt.comment_service.models.Comment;
import ba.unsa.etf.nwt.comment_service.models.Reply;
import ba.unsa.etf.nwt.comment_service.repository.ReplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public List<Reply> getReply() {
        return replyRepository.findAll();
    }

    public Reply addReply(Reply reply) {
        return replyRepository.save(reply);
    }

}
