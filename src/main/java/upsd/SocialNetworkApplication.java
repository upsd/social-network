package upsd;

import upsd.command_invoker.TextToCommandInterpreter;
import upsd.model.social_network.SocialNetwork;
import upsd.model.user.UserRepository;
import upsd.system_interfaces.Clock;
import upsd.system_interfaces.Console;

public class SocialNetworkApplication {

    public static void main(String[] args) {
        Console console = new Console();
        SocialNetwork socialNetwork = new SocialNetwork(console, new Clock(), new UserRepository());
        TextToCommandInterpreter textToCommandInterpreter = new TextToCommandInterpreter();

        while (true) {
            textToCommandInterpreter.interpretAndExecuteCommandFrom(console.readInput(), socialNetwork);
        }
    }
}
