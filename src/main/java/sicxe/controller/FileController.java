package sicxe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sicxe.service.file.FileService;

import java.io.IOException;

/**
 * Created by maciek on 08.11.15.
 */
@Controller
@Scope("session")
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile file) {
        String key = null;
        try {
            key = fileService.uploadAnonymousAsm(file);
        } catch (IOException e) {
            LOG.error(e.toString());
            e.printStackTrace();
        }
    }

}
