package sicxe.service.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sicxe.model.domain.AssemblyFile;
import sicxe.model.domain.Tutorial;
import sicxe.model.domain.User;
import sicxe.repository.TutorialRepository;
import sicxe.view.tutorial.NewTutorialDto;
import sicxe.view.tutorial.TutorialDto;
import sicxe.view.tutorial.TutorialFileDto;
import sicxe.view.tutorial.TutorialsDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by maciek on 28/01/16.
 */
@Service
public class TutorialService {
    private TutorialRepository tutorialRepository;

    @Autowired
    public TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public TutorialsDto findTutorials() {
        List<Tutorial> tutorials = tutorialRepository.findAll();

        return tutorialsIntoTutorialsDto(tutorials);
    }


    public TutorialDto findTutorial(Long id) {
        Optional<Tutorial> tutorial = tutorialRepository.findOne(id);
        return tutorialIntoTutorialDto(tutorial.get());
    }

    public TutorialDto saveTutorial(NewTutorialDto newTutorialDto, AssemblyFile file, User author) {
        Tutorial tutorial = new Tutorial();
        tutorial.getAssemblyFiles().add(file);
        tutorial.setAuthor(author);
        tutorial.setContent(newTutorialDto.getContent());
        tutorial.setTitle(newTutorialDto.getTitle());
        Tutorial saved = tutorialRepository.save(tutorial);
        return tutorialIntoTutorialDto(saved);
    }

    private TutorialsDto tutorialsIntoTutorialsDto(List<Tutorial> tutorials) {
        TutorialsDto tutorialsDto = new TutorialsDto();
        tutorials.forEach(tutorial -> {
            tutorialsDto.getTutorials().add(tutorialIntoTutorialDto(tutorial));
        });
        return tutorialsDto;
    }

    private TutorialDto tutorialIntoTutorialDto(Tutorial tutorial) {
        TutorialDto tutorialDto = new TutorialDto();
        tutorialDto.setAuthor(tutorial.getAuthor().getUsername());
        tutorialDto.setContent(tutorial.getContent());
        tutorialDto.setTitle(tutorial.getTitle());
        tutorialDto.setId(tutorial.getId());
        tutorial.getAssemblyFiles().forEach(assemblyFile -> {
            tutorialDto.getFiles()
                    .add(new TutorialFileDto(assemblyFile.getId(), assemblyFile.getName()));

        });
        return tutorialDto;
    }
}
