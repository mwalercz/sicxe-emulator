package sicxe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sicxe.model.domain.AssemblyFile;
import sicxe.model.domain.User;
import sicxe.service.file.FileService;
import sicxe.service.tutorial.TutorialService;
import sicxe.service.user.UserService;
import sicxe.view.tutorial.NewTutorialDto;
import sicxe.view.tutorial.TutorialDto;
import sicxe.view.tutorial.TutorialsDto;

import java.io.IOException;

/**
 * Created by maciek on 28/01/16.
 */
@RestController
public class TutorialController {

    private TutorialService tutorialService;
    private FileService fileService;
    private UserService userService;
    private static final Logger LOG = LoggerFactory.getLogger(TutorialController.class);

    @Autowired
    public TutorialController(TutorialService tutorialService, FileService fileService, UserService userService) {
        this.tutorialService = tutorialService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @RequestMapping(value = "/tutorials/all", method = RequestMethod.GET)
    public TutorialsDto findTutorials() {
        return tutorialService.findTutorials();
    }

    @RequestMapping(value = "/tutorials/get/{id}", method = RequestMethod.GET)
    public TutorialDto findTutorial(@PathVariable Long id) {
        return tutorialService.findTutorial(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/tutorials/save", method = RequestMethod.POST)
    public TutorialDto saveTutorial(@RequestParam("file") MultipartFile file,
                                    @RequestParam("author") String author,
                                    @RequestParam("content") String content,
                                    @RequestParam("title") String title){
        NewTutorialDto tutorial = new NewTutorialDto();
        tutorial.setAuthor(author);
        tutorial.setContent(content);
        tutorial.setTitle(title);
        User domainAuthor = userService.getUser(author);
        try {
            AssemblyFile assemblyFile = fileService.saveAsm(file, domainAuthor);
            return tutorialService.saveTutorial(tutorial, assemblyFile, domainAuthor);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            return null;
        }
    }
}
