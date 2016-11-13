package pl.gda.pg.ds.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gda.pg.ds.exceptions.UserAlreadyExistsException;
import pl.gda.pg.ds.models.User;
import pl.gda.pg.ds.repositories.UserRepository;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    private static Predicate<User> USER_EXISTS(String userName) {
        return u -> u.getName().equals(userName);
    }

    @Override
    @Transactional
    public User save(final User user) throws UserAlreadyExistsException {
        List<User> existing = repository.findAll();

        if (existing.stream().filter(USER_EXISTS(user.getName())).count() > 0) {
            throw new UserAlreadyExistsException(String.format("There already exists a user with name=%s", user.getName()));
        }

        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
