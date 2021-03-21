package ba.unsa.etf.nwt.user_service.requests;

import ba.unsa.etf.nwt.user_service.models.Answer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    private String surname;

    @NotBlank
    @Size(max = 100)
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    @Size(min = 4, max = 40)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotNull
    private Answer answer;

    public RegistrationRequest(@NotBlank @Size(min = 2, max = 40) String name, @NotBlank @Size(min = 3, max = 40) String surname, @NotBlank @Size(max = 100) @Email(message = "Email should be valid") String email, @NotBlank @Size(min = 4, max = 20) String username, @NotBlank @Size(min = 6, max = 20) String password, @NotNull Answer answer) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
