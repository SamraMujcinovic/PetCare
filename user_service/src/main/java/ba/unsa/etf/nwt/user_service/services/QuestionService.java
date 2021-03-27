package ba.unsa.etf.nwt.user_service.services;

import ba.unsa.etf.nwt.user_service.models.Question;
import ba.unsa.etf.nwt.user_service.repository.QuestionRepository;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private ValidationsService validationsService;

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Optional<Question> findById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public boolean existsById(Long questionId) {
        return questionRepository.existsById(questionId);
    }

    public Optional<Question> findByTitle(String title) {
        return questionRepository.findByTitle(title);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public ResponseMessage addQuestion(Question question){
        ResponseMessage rm = validationsService.validateQuestionRequest(question);
        if(!rm.getSuccess()){
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        try {
            questionRepository.findByTitle(question.getTitle())
                    .orElseThrow(() -> new RuntimeException("Question not found!"));

            return new ResponseMessage(false, "Question already exists!!", "BAD_REQUEST");
        }
        catch(RuntimeException e) {
            Question q = new Question();
            q.setTitle(question.getTitle());
            q.setDescription(question.getDescription());

            questionRepository.save(q);
            return new ResponseMessage(true, "Question added successfully!!", "OK");
        }
    }
}
