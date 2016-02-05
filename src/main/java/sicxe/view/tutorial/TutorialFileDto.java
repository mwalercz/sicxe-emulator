package sicxe.view.tutorial;

/**
 * Created by maciek on 28/01/16.
 */
public class TutorialFileDto {
    private Long id;
    private String filename;

    public TutorialFileDto(Long id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
