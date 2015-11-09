package sicxe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Created by maciek on 08.11.15.
 */
@Controller
public class FileController {

    /* @TODO
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String filename = "/asm/asm_" + uuid.toString();
        return null;

    }
}
