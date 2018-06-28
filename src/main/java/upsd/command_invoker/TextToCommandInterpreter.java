package upsd.command_invoker;

import upsd.model.social_network.SocialNetwork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextToCommandInterpreter {

    private final Pattern postPattern = Pattern.compile("(\\w*) -> (.*)");

    public void interpretAndExecuteCommandFrom(String command, SocialNetwork socialNetwork) {

        String[] words = command.split(" ");

        if (words.length == 1) {
            socialNetwork.read(command);
        }
        else if (words.length == 2) {
            socialNetwork.wall(words[0]);

        }
        else if (words.length == 3) {
            String follower = words[0];
            String toBeFollowed = words[2];
            socialNetwork.follow(follower, toBeFollowed);
        } else {
            Matcher postMatcher = postPattern.matcher(command);

            if (postMatcher.find()) {
                String userName = postMatcher.group(1);
                String message = postMatcher.group(2);
                socialNetwork.post(userName, message);
            }
        }
    }
}
