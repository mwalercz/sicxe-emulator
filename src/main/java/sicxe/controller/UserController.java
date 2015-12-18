package sicxe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sicxe.model.database.User;
import sicxe.repository.UserRepository;
import sicxe.service.common.NewUserStatus;
import sicxe.view.ViewUser;

import javax.persistence.EntityExistsException;
import java.util.Optional;

/**
 * Created by maciek on 30.11.15.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public
    @ResponseBody
    NewUserStatus newUser(@RequestBody ViewUser viewUser) {
        try {
            userRepository
                    .save(new User(viewUser.getUsername(), viewUser.getEmail(), viewUser.getPassword()));
        } catch (EntityExistsException e) {
            return NewUserStatus.ALREADY_EXIST;
        }
        return NewUserStatus.OK;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    ViewUser login(@RequestBody ViewUser viewUser) {
        Optional<User> maybeUser =
                userRepository.findByUsernameAndPassword(viewUser.getUsername(), viewUser.getPassword());
        /**
         * @TODO
         */
        return null;

    }
}
