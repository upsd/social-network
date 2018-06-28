package upsd.model.user;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class UserShould {

    @Test
    public void followAGivenUser() {
        User follower = new User("follower");

        follower.follow("to be followed");

        List<String> following = follower.following();

        assertEquals(asList("to be followed"), following);
    }
}
