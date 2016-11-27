package pl.gda.pg.ds.sruv.services;

import pl.gda.pg.ds.sruv.dtos.SimpleUser;
import pl.gda.pg.ds.sruv.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface UserService {
    void save(SimpleUser user) throws UserAlreadyExistsException;

    List<SimpleUser> findAll();
}
