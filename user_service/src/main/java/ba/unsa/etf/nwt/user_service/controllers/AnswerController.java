package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.exceptions.ResourceNotFoundException;
import ba.unsa.etf.nwt.user_service.models.Answer;
import ba.unsa.etf.nwt.user_service.services.AnswerService;
import ba.unsa.etf.nwt.user_service.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/answers")
    public List<Answer> getAnswers() {
        return answerService.findAll();
    }

    //samo user, autorizacija
    @GetMapping("/questions/{questionId}/answers")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.find(questionId);
    }

    //samo user, autorizacija
    @PostMapping("/questions/{questionId}/answers")
    public Answer addAnswer(@PathVariable Long questionId, @Valid @RequestBody Answer answer) {

        return questionService.findById(questionId)
                .map(question -> {
                    answer.setQuestion(question);
                    return answerService.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

}
