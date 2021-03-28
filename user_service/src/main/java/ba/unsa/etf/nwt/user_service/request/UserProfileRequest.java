package ba.unsa.etf.nwt.user_service.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserProfileRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    private String surname;

    @NotBlank
    @Size(min = 4, max = 40)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email(message = "Email should be valid")
    private String email;

    public UserProfileRequest(@NotBlank String name, @NotBlank String surname, @NotBlank String username, @NotBlank String email) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
