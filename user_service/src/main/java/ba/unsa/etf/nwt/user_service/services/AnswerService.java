package ba.unsa.etf.nwt.user_service.services;

import ba.unsa.etf.nwt.user_service.models.Answer;
import ba.unsa.etf.nwt.user_service.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
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
}
