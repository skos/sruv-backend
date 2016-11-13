package pl.gda.pg.ds.services;

import pl.gda.pg.ds.models.User;

import java.util.List;

public interface UserService {
    User save(User user);

    List<User> findAll();
}
