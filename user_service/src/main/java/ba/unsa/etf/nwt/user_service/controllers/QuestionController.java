package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.Question;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.user_service.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return questionService.findAll();
    }

    @PostMapping("/questions")
    public ResponseMessage createQuestion(@RequestBody Question question) {
        if(question.getTitle().equals("") && question.getDescription().equals("")) return new ResponseMessage(false, "Title and description can't be blank!!", "BAD_REQUEST");

        if(question.getTitle().length() < 2 || question.getTitle().length() > 150) return new ResponseMessage(false, "Title must be between 2 and 150 characters!!", "BAD_REQUEST");

        if(question.getDescription().equals("")) return new ResponseMessage(false, "Description can't be blank!!", "BAD_REQUEST");

        questionService.save(question);
        return new ResponseMessage(true, "Question added successfully!!", "OK");
    }
}
