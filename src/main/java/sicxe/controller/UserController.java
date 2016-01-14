package sicxe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sicxe.model.database.User;
import sicxe.repository.UserRepository;
import sicxe.service.common.NewUserStatus;
import sicxe.service.mappers.UserMapper;
import sicxe.view.ViewUser;

import javax.persistence.EntityExistsException;
import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by maciek on 30.11.15.
 */
@RestController
public class UserController {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private static final Logger LOG = LoggerFactory.getLogger(MachineController.class);


    @Autowired
    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public NewUserStatus newUser(@RequestBody ViewUser viewUser) {
        try {
            userRepository
                    .save(new User(viewUser.getUsername(), viewUser.getEmail(), viewUser.getPassword()));
        } catch (EntityExistsException e) {
            LOG.error("user already exists", e);
            return NewUserStatus.ALREADY_EXIST;
        }
        return NewUserStatus.OK;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ViewUser login(@RequestBody ViewUser viewUser) {
        Optional<User> maybeUser =
                userRepository.findByUsernameAndPassword(viewUser.getUsername(), viewUser.getPassword());
        try {
            return userMapper.userToViewUser(maybeUser.get());
        } catch (NoSuchElementException e) {
            LOG.info("no such user in db", e);
            return new ViewUser();
        }
    }

    @RequestMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}
