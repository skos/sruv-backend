package pl.gda.pg.ds.sruv.controllers;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.gda.pg.ds.sruv.dtos.SimpleUser;
import pl.gda.pg.ds.sruv.services.UserService;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
        final SimpleUser user = new SimpleUser(USER_NAME);
        userController.createUser(user);

        verify(userService, times(1)).save(user);
    }

    @Test
    public void testFindAll_shouldReturnEmptyList() throws Exception {
        final List<SimpleUser> expectedUsers = Lists.emptyList();
        List<SimpleUser> returnedUsers = userController.findAll();

        verify(userService, times(1)).findAll();
        assertEquals("Returned users should come from the service", expectedUsers, returnedUsers);
    }
}