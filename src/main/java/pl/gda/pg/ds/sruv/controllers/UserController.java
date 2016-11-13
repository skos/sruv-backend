package pl.gda.pg.ds.sruv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.gda.pg.ds.sruv.exceptions.UserAlreadyExistsException;
import pl.gda.pg.ds.sruv.models.User;
import pl.gda.pg.ds.sruv.services.UserService;

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
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody  final User user) throws UserAlreadyExistsException {
        return service.save(user);
    }
}
