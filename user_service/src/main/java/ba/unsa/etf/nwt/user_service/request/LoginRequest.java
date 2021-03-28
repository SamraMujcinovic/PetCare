package ba.unsa.etf.nwt.user_service.request;

import ba.unsa.etf.nwt.user_service.annotation.PasswordValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequest {
    @NotBlank
    @Size(max = 100)
    private String usernameOrEmail;

    @NotBlank
    @Size(min = 6, max = 40)
    //@PasswordValidation
    private String password;

    public LoginRequest(@NotBlank @Size(max = 100) String usernameOrEmail, @NotBlank @Size(min = 6, max = 40) String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
