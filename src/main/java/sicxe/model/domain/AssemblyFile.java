package sicxe.model.domain;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 30.11.15.
 */
@Entity
public class AssemblyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String amazonKey;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    @ManyToMany(mappedBy = "assemblyFiles")
    private List<Tutorial> tutorials = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAmazonKey() {
        return amazonKey;
    }

    public void setAmazonKey(String amazonKey) {
        this.amazonKey = amazonKey;
    }


    public List<Tutorial> getTutorials() {
        return tutorials;
    }

    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }
}
