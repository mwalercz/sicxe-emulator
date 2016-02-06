package sicxe.model.domain;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 28/01/16.
 */
@Entity
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Lob
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "file_tutorial", joinColumns = @JoinColumn(name = "tutorial_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"))
    private List<AssemblyFile> assemblyFiles = new LinkedList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<AssemblyFile> getAssemblyFiles() {
        return assemblyFiles;
    }

    public void setAssemblyFiles(List<AssemblyFile> assemblyFiles) {
        this.assemblyFiles = assemblyFiles;
    }
}
