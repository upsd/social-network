package upsd.model.social_network;

import upsd.model.post.Post;
import upsd.model.user.User;
import upsd.model.user.UserRepository;
import upsd.system_interfaces.Clock;
import upsd.system_interfaces.Console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SocialNetwork {

    private final Console console;
    private final Clock clock;

    private final List<Post> posts = new ArrayList<>();

    private final UserRepository userRepository;

    public SocialNetwork(Console console, Clock clock, UserRepository userRepository) {
        this.console = console;
        this.clock = clock;
        this.userRepository = userRepository;
    }

    public void post(String username, String message) {
        if (!this.userRepository.get(username).isPresent()) {
            try {
                this.userRepository.add(username);
            } catch (UserRepository.UserAlreadyExistsException e) {
                e.printStackTrace();
            }
        }

        User user = this.userRepository.get(username).get();
        this.posts.add(new Post(user, message, clock.now()));
    }

    private void printPosts(boolean shouldPrintPostAuthor, List<Post> posts) {
        posts
                .forEach(p -> {
                    int timeSincePosted = clock.calculateTimeDifferenceInMinutes(p.timePosted());

                    String timeSinceText = " minutes ago";
                    if (timeSincePosted == 1) {
                        timeSinceText = " minute ago";
                    }

                    String postAuthorText = "";
                    if (shouldPrintPostAuthor) {
                        postAuthorText = p.user().name() + " - ";
                    }

                    console.printLine(
                            postAuthorText
                                    + p.message()
                                    + " ("
                                    + timeSincePosted
                                    + timeSinceText
                                    + ")");
                });
    }

    public void read(String username) {
        List<Post> userPosts = posts
                .stream()
                .filter(p -> p.user().name().equals(username))
                .collect(Collectors.toList());
        Collections.reverse(userPosts);

        printPosts(false, userPosts);
    }

    public void wall(String user) {
        User matchingUser = this.userRepository.get(user).get();

        List<User> currentlyFollowing = getFollowingOfUser(user);
        List<Post> userPosts = posts
                .stream()
                .filter(p -> p.user().name().equals(matchingUser.name()) || currentlyFollowing.contains(p.user()))
                .collect(Collectors.toList());
        Collections.reverse(userPosts);

        printPosts(true, userPosts);
    }

    public void follow(String follower, String toBeFollowed) {
        Optional<User> matchingUser = userRepository.get(follower);
        if (!matchingUser.isPresent()) {
            try {
                userRepository.add(follower);
            } catch (UserRepository.UserAlreadyExistsException e) {
                e.printStackTrace();
            }
        }

        User user = userRepository.get(follower).get();
        user.follow(toBeFollowed);
        userRepository.update(follower, user);
    }

    private List<User> getFollowingOfUser(String usernameToGetWhoTheyAreFollowing) {
        List<User> following = new ArrayList<>();

        userRepository.get(usernameToGetWhoTheyAreFollowing).get().following().stream().forEach(name -> {
            User user = userRepository.get(name).get();
            following.add(user);
        });

        return following;
    }
}