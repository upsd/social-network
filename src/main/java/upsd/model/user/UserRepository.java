package upsd.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository {

    List<User> users = new ArrayList<>();

    public void add(String username) throws UserAlreadyExistsException {
        int numberOfUsersWithSameUsername = this.users.stream().filter(u -> u.name().equals(username)).collect(Collectors.toList()).size();
        if (numberOfUsersWithSameUsername > 0) {
            throw new UserAlreadyExistsException(username);

        }
        users.add(new User(username));
    }

    public Optional<User> get(String username) {
        return this.users
                .stream()
                .filter(u -> u.name().equals(username))
                .findFirst();
    }

    public void update(String usernameOfUserToUpdate, User newUserObject) {
        int indexToUpdate = users.indexOf(get(usernameOfUserToUpdate).get());
        users.set(indexToUpdate, newUserObject);
    }

    public static class UserAlreadyExistsException extends Exception {

        public UserAlreadyExistsException(String username) {
            super(username + " already exists, please choose a different one.");
        }
    }
}
