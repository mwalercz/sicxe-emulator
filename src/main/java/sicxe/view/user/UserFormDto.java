package sicxe.view.user;

import org.hibernate.validator.constraints.NotEmpty;
import sicxe.service.validation.email.ValidEmail;
import sicxe.service.validation.password.PasswordMatches;

import javax.validation.constraints.NotNull;

/**
 * Created by maciek on 30.11.15.
 */
@PasswordMatches
public class UserFormDto {

    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;

    private Boolean admin;

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
