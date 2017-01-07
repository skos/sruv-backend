package pl.gda.pg.ds.sruv.services.impls;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gda.pg.ds.sruv.dtos.SimpleUser;
import pl.gda.pg.ds.sruv.exceptions.UserAlreadyExistsException;
import pl.gda.pg.ds.sruv.models.User;
import pl.gda.pg.ds.sruv.repositories.UserRepository;
import pl.gda.pg.ds.sruv.services.UserService;

import java.util.List;
import java.util.function.Predicate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    private static Predicate<User> USER_EXISTS(String userName) {
        return u -> u.getLogin().equals(userName);
    }

    @Override
    @Transactional
    public void save(final SimpleUser user) throws UserAlreadyExistsException {
        List<User> existing = repository.findAll();

        if (existing.stream().filter(USER_EXISTS(user.getLogin())).count() > 0) {
            throw new UserAlreadyExistsException(String.format("There already exists a user with name '%s'", user.getLogin()));
        }

        repository.save(new User(user.getLogin()));
    }

    @Override
    public List<SimpleUser> findAll() {
        List<SimpleUser> users = Lists.newArrayList();
        repository.findAll().forEach(user -> users.add(new SimpleUser(user.getLogin())));

        return users;
    }
}
