package upsd.command_invoker;

import org.junit.Test;
import upsd.model.social_network.SocialNetwork;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TextToCommandInterpreterShould {

    @Test
    public void identifyCommandWithinText() {
        String posting = "Alice -> I love coding";
        String reading = "Bob";
        String following = "Charlie follows Alice";
        String walling = "Charlie wall";

        TextToCommandInterpreter interpreter = new TextToCommandInterpreter();
        SocialNetwork socialNetwork = mock(SocialNetwork.class);

        interpreter.interpretAndExecuteCommandFrom(posting, socialNetwork);
        interpreter.interpretAndExecuteCommandFrom(reading, socialNetwork);
        interpreter.interpretAndExecuteCommandFrom(following, socialNetwork);
        interpreter.interpretAndExecuteCommandFrom(walling, socialNetwork);

        verify(socialNetwork).post("Alice", "I love coding");
        verify(socialNetwork).read("Bob");
        verify(socialNetwork).follow("Charlie", "Alice");
        verify(socialNetwork).wall("Charlie");
    }
}
