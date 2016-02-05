package sicxe.view.tutorial;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 28/01/16.
 */
public class TutorialDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private List<TutorialFileDto> files = new LinkedList<>();

    public List<TutorialFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<TutorialFileDto> files) {
        this.files = files;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
