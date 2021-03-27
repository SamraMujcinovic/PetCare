package ba.unsa.etf.nwt.user_service.services;

import ba.unsa.etf.nwt.user_service.models.Answer;
import ba.unsa.etf.nwt.user_service.repository.AnswerRepository;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private QuestionService questionService;

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    public List<Answer> find(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public Answer save(Answer answer) {
        return  answerRepository.save(answer);
    }

    public ResponseMessage addAnswer(Long questionId, Answer answer){

        if(answer.getText().isEmpty() || answer.getText().length() > 100) {
            return new ResponseMessage(false, "Answer not valid!!",
                    "BAD_REQUEST");
        }

        try {
            questionService.findById(questionId).map(question -> {
                answer.setQuestion(question);
                answerRepository.save(answer);
                return new ResponseMessage(true, "Answer added successfully!!", "OK");
            }).orElseThrow(() -> new RuntimeException("There is no question with that ID!!"));
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, "There is no question with that ID!!", "NOT_FOUND");
        }

        return new ResponseMessage(true, "Answer added successfully!!", "OK");
    }
}
