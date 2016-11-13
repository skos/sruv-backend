package pl.gda.pg.ds.services;

import pl.gda.pg.ds.exceptions.UserAlreadyExistsException;
import pl.gda.pg.ds.models.User;

import java.util.List;

public interface UserService {
    User save(User user) throws UserAlreadyExistsException;

    List<User> findAll();
}
