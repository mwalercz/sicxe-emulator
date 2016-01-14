package sicxe.service.mappers;

import org.mapstruct.Mapper;
import sicxe.model.database.User;
import sicxe.view.ViewUser;

/**
 * Created by maciek on 05/01/16.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    ViewUser userToViewUser(User user);

}
