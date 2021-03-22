package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.Answer;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.user_service.services.AnswerService;
import ba.unsa.etf.nwt.user_service.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    //admin
    @GetMapping("/answers")
    public List<Answer> getAnswers() {
        return answerService.findAll();
    }

    //user
    @GetMapping("/questions/{questionId}/answers")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.find(questionId);
    }

    //user
    @PostMapping("/questions/{questionId}/answer")
    public ResponseMessage createAnswer(@PathVariable Long questionId, @RequestBody Answer answer) {
        if(answer.getText().isEmpty() || answer.getText().length() > 100) {
            return new ResponseMessage(false, "Answer not valid!!",
                    "BAD_REQUEST");
        }

        try {
            questionService.findById(questionId).map(question -> {
                        answer.setQuestion(question);
                        answerService.save(answer);
                        return new ResponseMessage(true, "Answer added successfully!!", "OK");
                    }).orElseThrow(() -> new RuntimeException("There is no question with that ID!!"));
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, "There is no question with that ID!!", "NOT_FOUND");
        }

        return new ResponseMessage(true, "Answer added successfully!!", "OK");
    }
}
