package sicxe.view.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciek on 28/01/16.
 */
public class UserDto {

    private String username;
    private String email;
    private Boolean admin;
    List<UserFilesDto> assemblyFiles = new ArrayList<>();
    List<UserTutorialDto> tutorials = new ArrayList<>();

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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }


    public List<UserFilesDto> getAssemblyFiles() {
        return assemblyFiles;
    }

    public void setAssemblyFiles(List<UserFilesDto> assemblyFiles) {
        this.assemblyFiles = assemblyFiles;
    }

    public List<UserTutorialDto> getTutorials() {
        return tutorials;
    }

    public void setTutorials(List<UserTutorialDto> tutorials) {
        this.tutorials = tutorials;
    }
}
