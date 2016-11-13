package pl.gda.pg.ds.sruv.services;

import pl.gda.pg.ds.sruv.exceptions.UserAlreadyExistsException;
import pl.gda.pg.ds.sruv.models.User;

import java.util.List;

public interface UserService {
    User save(User user) throws UserAlreadyExistsException;

    List<User> findAll();
}
