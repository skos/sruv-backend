package pl.gda.pg.ds.sruv.services;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.gda.pg.ds.sruv.dtos.SimpleUser;
import pl.gda.pg.ds.sruv.exceptions.UserAlreadyExistsException;
import pl.gda.pg.ds.sruv.models.User;
import pl.gda.pg.ds.sruv.repositories.UserRepository;
import pl.gda.pg.ds.sruv.services.impls.UserServiceImpl;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Resource
    private UserServiceImpl userService;

    private static final String USER_NAME = "testUser";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        userService = new UserServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave_shouldSaveNewUser() throws Exception {
        final SimpleUser user = new SimpleUser(USER_NAME);
        userService.save(user);

        verify(userRepository, times(1)).save(any(User.class)); // TODO something better needed :)
    }

    @Test
    public void testFindAll_shouldReturnEmptyList() throws Exception {
        final List<SimpleUser> expectedUsers = Lists.emptyList();
        List<SimpleUser> returnedUsers = userService.findAll();

        verify(userRepository, times(1)).findAll();
        assertEquals("Returned users should come from the repository", expectedUsers, returnedUsers);
    }

    @Test
    public void shouldSaveNewUser_GivenThereExistsOneWithTheSameId_ThenTheExceptionShouldBeThrown() throws Exception {
        thrown.expect(UserAlreadyExistsException.class);
        final List<User> user = Lists.newArrayList(new User(USER_NAME));
        when(userRepository.findAll()).thenReturn(user);

        userService.save(new SimpleUser(USER_NAME));
        verify(userRepository, never()).save(any(User.class));
    }
}