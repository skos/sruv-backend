package pl.gda.pg.ds.sruv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.gda.pg.ds.sruv.dtos.SimpleUser;
import pl.gda.pg.ds.sruv.exceptions.UserAlreadyExistsException;
import pl.gda.pg.ds.sruv.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<SimpleUser> findAll(){
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid final SimpleUser user) throws UserAlreadyExistsException {
        service.save(user);
    }
}
