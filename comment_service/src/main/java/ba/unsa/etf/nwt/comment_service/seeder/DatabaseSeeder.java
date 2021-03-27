package ba.unsa.etf.nwt.comment_service.seeder;

import ba.unsa.etf.nwt.comment_service.models.Comment;
import ba.unsa.etf.nwt.comment_service.models.Reply;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.MainRole;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.SectionRoleName;
import ba.unsa.etf.nwt.comment_service.services.CommentService;
import ba.unsa.etf.nwt.comment_service.services.MainRoleService;
import ba.unsa.etf.nwt.comment_service.services.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseSeeder {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private MainRoleService mainRoleService;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedDatabase();
    }

    private void seedDatabase() {
        MainRole r1 = createRole(SectionRoleName.ROLE_CATEGORY);
        MainRole r2 = createRole(SectionRoleName.ROLE_PET);

        Comment c1 = createComment( Long.valueOf(1), "Question", "What kind of dog do you want?", r2);
        Reply rp1 = createReply(Long.valueOf(2), c1, "Sweet and relaxed, friendly towards everyone.");
        Reply rp2 = createReply(Long.valueOf(3), c1, "Amazing!");

    }

    private MainRole createRole(SectionRoleName sectionRoleName) {
        MainRole r = new MainRole(sectionRoleName);
        mainRoleService.addRole(r);
        return r;
    }

    private Comment createComment(Long UserID, String title, String content, MainRole role) {
        Comment comment = new Comment();
        comment.setUserID(UserID);
        comment.setTitle(title);
        comment.setContent(content);
        comment.setRoles(role);
        commentService.addComment(comment, role.getId());
        return comment;
    }

    private Reply createReply(Long userID, Comment comment, String content) {
        Reply reply = new Reply();
        reply.setUserID(userID);
        reply.setComment(comment);
        reply.setContent(content);
        replyService.addReply(reply, comment.getId());
        return reply;
    }

}
