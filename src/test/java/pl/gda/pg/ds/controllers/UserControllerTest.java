package pl.gda.pg.ds.controllers;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.gda.pg.ds.exceptions.UserAlreadyExistsException;
import pl.gda.pg.ds.models.User;
import pl.gda.pg.ds.services.UserService;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    @Resource
    private UserController userController;

    private static final String USER_NAME = "testUser";

    @Before
    public void setUp() {
        userController = new UserController();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave_shouldCreateUser() throws Exception {
        final User savedUser = stubServiceToReturnStoredUser();
        final User user = new User(USER_NAME);
        User returnedUser = userController.createUser(user);

        verify(userService, times(1)).save(user);
        assertEquals("Returned user should come from the service", savedUser, returnedUser);
    }

    @Test
    public void testFindAll_shouldReturnEmptyList() throws Exception {
        final List<User> expectedUsers = Lists.emptyList();
        List<User> returnedUsers = userController.findAll();

        verify(userService, times(1)).findAll();
        assertEquals("Returned users should come from the service", expectedUsers, returnedUsers);
    }

    private User stubServiceToReturnStoredUser() throws Exception {
        final User user = new User(USER_NAME);
        when(userService.save(any(User.class))).thenReturn(user);
        return user;
    }
}