package ba.unsa.etf.nwt.comment_service;

import ba.unsa.etf.nwt.comment_service.models.Comment;
import ba.unsa.etf.nwt.comment_service.models.Reply;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.MainRole;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.SectionRoleName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CommentServiceApplicationTests {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void CreateCommentNoValues() {
        Comment comment = new Comment();
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CreateCommentWithOnlyUserId() {
        Comment comment = new Comment();
        comment.setUserID(1L);
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CreateCommentWithOnlyTitle() {
        Comment comment = new Comment();
        comment.setTitle("Create Comment With Only Title");
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CreateCommentWithOnlyContent() {
        Comment comment = new Comment();
        comment.setContent("Content 1");
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void TitleShort() {
        Comment comment = new Comment();
        MainRole r2 = new MainRole(SectionRoleName.ROLE_CATEGORY);
        Set<MainRole> role2 = new HashSet<>();
        role2.add(r2);

        comment.setUserID(1L);
        comment.setTitle("Q");
        comment.setContent( "Favorite animal?");
        comment.setRoles(role2);

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void ContentShort() {
        Comment comment = new Comment();
        MainRole r2 = new MainRole(SectionRoleName.ROLE_CATEGORY);
        Set<MainRole> role2 = new HashSet<>();
        role2.add(r2);

        comment.setUserID(1L);
        comment.setTitle("Question 13");
        comment.setContent( "Fav");
        comment.setRoles(role2);

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CommentWithNoRole() {
        Comment comment = new Comment();
        MainRole r2 = new MainRole(SectionRoleName.ROLE_CATEGORY);

        comment.setUserID(1L);
        comment.setTitle("Question 13");
        comment.setContent( "Fav");

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CorrectComment() {
        Comment comment = new Comment();
        MainRole r2 = new MainRole(SectionRoleName.ROLE_CATEGORY);
        Set<MainRole> role2 = new HashSet<>();
        role2.add(r2);

        comment.setUserID(1L);
        comment.setTitle("Question 13");
        comment.setContent( "Favorite animal?");
        comment.setRoles(role2);

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void NullReply() {
        Reply reply = new Reply();
        Set<ConstraintViolation<Reply>> violations = validator.validate(reply);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CreateReplyWithOnlyContent() {
        Reply reply = new Reply();
        reply.setContent("Reply 1");
        Set<ConstraintViolation<Reply>> violations = validator.validate(reply);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CreateReplyWithOnlyUserId() {
        Reply reply = new Reply();
        reply.setUserID(2L);
        Set<ConstraintViolation<Reply>> violations = validator.validate(reply);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void ShortContent() {
        Comment comment = new Comment();
        MainRole r2 = new MainRole(SectionRoleName.ROLE_CATEGORY);
        Set<MainRole> role2 = new HashSet<>();
        role2.add(r2);

        comment.setUserID(1L);
        comment.setTitle("Question 13");
        comment.setContent( "Favorite animal?");
        comment.setRoles(role2);
        Reply reply = new Reply(comment, Long.valueOf(1), "No");

        Set<ConstraintViolation<Reply>> violations = validator.validate(reply);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CorrectReply() {
        Comment comment = new Comment();
        MainRole r2 = new MainRole(SectionRoleName.ROLE_CATEGORY);
        Set<MainRole> role2 = new HashSet<>();
        role2.add(r2);

        comment.setUserID(1L);
        comment.setTitle("Question 13");
        comment.setContent( "Favorite animal?");
        comment.setRoles(role2);
        Reply reply = new Reply(comment, Long.valueOf(1), "Amazing");

        Set<ConstraintViolation<Reply>> violations = validator.validate(reply);
        assertTrue(violations.isEmpty());
    }
}
