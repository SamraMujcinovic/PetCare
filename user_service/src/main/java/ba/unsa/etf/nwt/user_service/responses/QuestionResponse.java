package ba.unsa.etf.nwt.user_service.responses;

import ba.unsa.etf.nwt.user_service.models.Question;

public class QuestionResponse {
    private Boolean success;
    private String message;
    private String status;
    private Question question;

    public QuestionResponse(Boolean success, String message, String status, Question question) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.question = question;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
