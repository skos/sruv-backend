package pl.gda.pg.ds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.gda.pg.ds.exceptions.UserAlreadyExistsException;
import pl.gda.pg.ds.models.User;
import pl.gda.pg.ds.services.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll(){
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody  final User user) throws UserAlreadyExistsException {
        return service.save(user);
    }
}
