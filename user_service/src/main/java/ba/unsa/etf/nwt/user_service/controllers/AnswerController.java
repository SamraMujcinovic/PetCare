package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.Answer;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.user_service.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerController {
    @Autowired
    private AnswerService answerService;


    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    //admin
    @GetMapping("/questions/{questionId}/answers")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.find(questionId);
    }

    //admin
    @PostMapping("/questions/{questionId}/answer")
    public ResponseMessage createAnswer(@PathVariable Long questionId, @RequestBody Answer answer) {
        return answerService.addAnswer(questionId, answer);
    }
}
