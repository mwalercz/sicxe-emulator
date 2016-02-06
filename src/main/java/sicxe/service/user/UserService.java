package sicxe.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sicxe.model.domain.User;
import sicxe.repository.UserRepository;
import sicxe.view.user.*;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by maciek on 28/01/16.
 */
@Service
public class UserService {


    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User registerNewUserAccount(UserFormDto accountDto) throws EmailAlreadyExists, UsernameAlreadyExists {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailAlreadyExists();
        }
        if (usernameExists(accountDto.getUsername())) {
            throw new UsernameAlreadyExists();
        }
        User user = new User();
        user.setEmail(accountDto.getEmail());
        user.setPassword(accountDto.getPassword());
        user.setUsername(accountDto.getUsername());
        if (user.getAdmin() != null) user.setAdmin(accountDto.getAdmin());
        else user.setAdmin(false);

        userRepository.save(user);
        return user;
    }

    public UserLoginDto login(UserFormDto userFormDto) throws NoSuchElementException{
        Optional<User> maybeUser =
                userRepository.findByEmailAndPassword(userFormDto.getEmail(), userFormDto.getPassword());
        if(maybeUser.isPresent()) {
            UserLoginDto userLoginDto = new UserLoginDto();
            userLoginDto.setUsername(maybeUser.get().getUsername());
            return userLoginDto;
        }
        else throw new NoSuchElementException();
    }

    public UserDto getDtoUser(String username){
        Optional<User> user = userRepository.findByUsername(username);
        UserDto userDto = new UserDto();
        if(user.isPresent()){
            userDto.setUsername(user.get().getUsername());
            userDto.setAdmin(user.get().getAdmin());
            userDto.setEmail(user.get().getEmail());

            user.get().getAssemblyFiles().forEach(assemblyFile -> {
                UserFilesDto filesDto = new UserFilesDto();
                filesDto.setId(assemblyFile.getId());
                filesDto.setName(assemblyFile.getName());
                userDto.getAssemblyFiles().add(filesDto);
            });

            user.get().getTutorials().forEach(article -> {
                UserTutorialDto tutorialDto = new UserTutorialDto();
                tutorialDto.setId(article.getId());
                tutorialDto.setTitle(article.getTitle());
                userDto.getTutorials().add(tutorialDto);
            });
            return userDto;
        } else return null;
    }

    public User getUser(String username){
        return userRepository.findByUsername(username).get();
    }

    private boolean emailExist(String email) {
        Optional user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    private boolean usernameExists(String username) {
        Optional user = userRepository.findByUsername(username);
        return user.isPresent();

    }

}
