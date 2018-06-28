package upsd.model.social_network;

import org.junit.Test;
import org.mockito.InOrder;
import upsd.model.user.UserRepository;
import upsd.system_interfaces.Clock;
import upsd.system_interfaces.Console;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class SocialNetworkShould {

    private final Console console = mock(Console.class);
    private final Clock clock = mock(Clock.class);
    private final UserRepository userRepository = new UserRepository();
    private final SocialNetwork socialNetwork = new SocialNetwork(console, clock, userRepository);

    @Test
    public void canPostAMessageAndThenRead() {
        String message = "Hello World!";

        given(clock.now()).willReturn(LocalDateTime.now());
        given(clock.calculateTimeDifferenceInMinutes(clock.now())).willReturn(2);

        socialNetwork.post("bob", message);
        socialNetwork.read("bob");

        verify(console).printLine(message + " (2 minutes ago)");
    }

    @Test
    public void canPostMultipleMessagesAndThenReadWithMostRecentAtTheTop() {
        given(clock.now()).willReturn(LocalDateTime.now());
        given(clock.calculateTimeDifferenceInMinutes(clock.now())).willReturn(1, 5);

        socialNetwork.post("bob", "Hello World");
        socialNetwork.post("bob", "Hello again");
        socialNetwork.read("bob");

        verify(console).printLine("Hello again (1 minute ago)");
        verify(console).printLine("Hello World (5 minutes ago)");
    }

    @Test
    public void canViewWallForAGivenUserWhenTheyAreNotFollowingAnyone() {
        given(clock.now()).willReturn(LocalDateTime.now());
        given(clock.calculateTimeDifferenceInMinutes(clock.now())).willReturn(1, 5);

        socialNetwork.post("bob", "My First post.");
        socialNetwork.post("bob", "My Second post.");
        socialNetwork.wall("bob");

        verify(console).printLine("bob - My Second post. (1 minute ago)");
        verify(console).printLine("bob - My First post. (5 minutes ago)");
    }

    @Test
    public void canViewWallWhenFollowingOtherUsers() {
        given(clock.now()).willReturn(LocalDateTime.now());
        given(clock.calculateTimeDifferenceInMinutes(clock.now())).willReturn(1, 10);

        socialNetwork.follow("bob", "alice");
        socialNetwork.post("bob", "I am following Alice.");
        socialNetwork.post("alice", "I love this.");
        socialNetwork.wall("bob");

        InOrder inOrder = inOrder(console , console);
        inOrder.verify(console).printLine(eq("alice - I love this. (1 minute ago)"));
        inOrder.verify(console).printLine(eq("bob - I am following Alice. (10 minutes ago)"));
    }
}
