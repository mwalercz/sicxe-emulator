package sicxe.view.tutorial;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 28/01/16.
 */
public class TutorialsDto {
    List<TutorialDto> tutorials = new LinkedList<>();

    public List<TutorialDto> getTutorials() {
        return tutorials;
    }

    public void setTutorials(List<TutorialDto> tutorials) {
        this.tutorials = tutorials;
    }
}
