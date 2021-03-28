package ba.unsa.etf.nwt.user_service.controller;

import ba.unsa.etf.nwt.user_service.model.Question;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import ba.unsa.etf.nwt.user_service.service.QuestionService;
import ba.unsa.etf.nwt.user_service.service.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ValidationsService validationsService;

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return questionService.findAll();
    }

    //admin
    @PostMapping("/questions")
    public ResponseMessage createQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }
}
