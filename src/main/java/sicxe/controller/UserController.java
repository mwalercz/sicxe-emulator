package sicxe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sicxe.view.user.*;
import sicxe.service.user.EmailAlreadyExists;
import sicxe.service.user.UserService;
import sicxe.service.user.UsernameAlreadyExists;

import javax.validation.Valid;
import java.util.NoSuchElementException;

/**
 * Created by maciek on 30.11.15.
 */
@RestController
public class UserController {

    private UserService userService;
    private static final Logger LOG = LoggerFactory.getLogger(MachineController.class);


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserStatus registerAccount(@RequestBody @Valid UserFormDto userFormDto) {

        try {
            userService.registerNewUserAccount(userFormDto);
        } catch (EmailAlreadyExists emailAlreadyExists) {
            emailAlreadyExists.printStackTrace();
            LOG.info(emailAlreadyExists.toString());
            return new UserStatus(UserStatusEnum.EMAIL_ALREADY_EXIST);
        } catch (UsernameAlreadyExists usernameAlreadyExists) {
            usernameAlreadyExists.printStackTrace();
            LOG.info(usernameAlreadyExists.toString());
            return new UserStatus(UserStatusEnum.USERNAME_ALREADY_EXIST);
        }
        return new UserStatus(UserStatusEnum.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserLoginDto login(@RequestBody UserFormDto userFormDto) {
        try {
            return userService.login(userFormDto);
        } catch (NoSuchElementException e){
            LOG.debug("no user in db");
            return null;
        }

    }
    @RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
    public UserDto getUser(@PathVariable String username){
        return userService.getDtoUser(username);
    }


}
