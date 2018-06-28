package upsd.model.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRepositoryShould {

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void addANewUser() throws UserRepository.UserAlreadyExistsException {
        userRepository.add("bob");

        User matchingUser = userRepository.get("bob").get();

        assertEquals("bob", matchingUser.name());
    }

    @Test(expected = UserRepository.UserAlreadyExistsException.class)
    public void notAddUserWhenOneAlreadyExistsWithSameName() throws UserRepository.UserAlreadyExistsException {
        userRepository.add("bob");
        userRepository.add("bob");
    }

    @Test
    public void addMoreThanOneUser() throws UserRepository.UserAlreadyExistsException {
        userRepository.add("bob");
        userRepository.add("alice");

        User matchingBob = userRepository.get("bob").get();
        User matchingAlice = userRepository.get("alice").get();

        assertEquals("bob", matchingBob.name());
        assertEquals("alice", matchingAlice.name());
    }

    @Test
    public void canAddFollowingToAUser() throws UserRepository.UserAlreadyExistsException {
        userRepository.add("bob");
        User user = userRepository.get("bob").get();
        user.follow("alice");
        userRepository.update("bob", user);

        User updatedUser = userRepository.get("bob").get();

        assertEquals(1, updatedUser.following().size());
    }
}
