package ba.unsa.etf.nwt.user_service.request.password_requests;

import ba.unsa.etf.nwt.user_service.annotation.PasswordValidation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordChangeRequest {
    @NotBlank
    @Size(max = 100)
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    private String answer;

    @NotBlank
    @Size(min = 6, max = 40)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 40)
    @PasswordValidation
    private String newPassword;

    public PasswordChangeRequest(@NotBlank @Size(max = 100) @Email(message = "Email should be valid") String email, @NotBlank String answer, @NotBlank @Size(min = 6, max = 40) String oldPassword, @NotBlank @Size(min = 6, max = 40) String newPassword) {
        this.email = email;
        this.answer = answer;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
