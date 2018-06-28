import org.junit.Test;
import upsd.command_invoker.TextToCommandInterpreter;
import upsd.model.social_network.SocialNetwork;
import upsd.model.user.UserRepository;
import upsd.system_interfaces.Clock;
import upsd.system_interfaces.Console;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AcceptanceTests {

    private Console console = mock(Console.class);
    private TextToCommandInterpreter textToCommandInterpreter = new TextToCommandInterpreter();
    private Clock clock = mock(Clock.class);
    private UserRepository userRepository = new UserRepository();
    private SocialNetwork socialNetwork = new SocialNetwork(console, clock, userRepository);

    @Test
    public void userCanPostToTimeline() {
        given(console.readInput()).willReturn("Bob -> I love this", "Bob");
        given(clock.now()).willReturn(LocalDateTime.now());
        given(clock.calculateTimeDifferenceInMinutes(clock.now())).willReturn(1);

        textToCommandInterpreter.interpretAndExecuteCommandFrom(console.readInput(), socialNetwork);
        textToCommandInterpreter.interpretAndExecuteCommandFrom(console.readInput(), socialNetwork);

        verify(console).printLine("I love this (1 minute ago)");
    }

    @Test
    public void userWallShowsOtherUserPostWhenFollowingOthers() {
        given(console.readInput()).willReturn("Bob -> I love this", "Bob follows Alice", "Alice -> Hello all", "Bob wall");
        given(clock.now()).willReturn(LocalDateTime.now());
        given(clock.calculateTimeDifferenceInMinutes(clock.now())).willReturn(2, 5);

        textToCommandInterpreter.interpretAndExecuteCommandFrom(console.readInput(), socialNetwork);
        textToCommandInterpreter.interpretAndExecuteCommandFrom(console.readInput(), socialNetwork);
        textToCommandInterpreter.interpretAndExecuteCommandFrom(console.readInput(), socialNetwork);
        textToCommandInterpreter.interpretAndExecuteCommandFrom(console.readInput(), socialNetwork);

        verify(console).printLine("Alice - Hello all (2 minutes ago)");
        verify(console).printLine("Bob - I love this (5 minutes ago)");
    }
}