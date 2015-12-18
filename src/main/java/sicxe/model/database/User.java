package sicxe.model.database;

import javax.persistence.*;
import java.util.List;

/**
 * Created by maciek on 29.11.15.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private Boolean admin;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<AssemblyFile> assemblyFiles;

    public User(String username, String email, Boolean admin, String password) {
        this.username = username;
        this.email = email;
        this.admin = admin;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<AssemblyFile> getAssemblyFiles() {
        return assemblyFiles;
    }

    public void setAssemblyFiles(List<AssemblyFile> assemblyFiles) {
        this.assemblyFiles = assemblyFiles;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public String getPassword() {
        return password;
    }
}
