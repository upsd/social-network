package upsd.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private final String name;
    private List<String> following = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public void follow(String toBeFollowed) {
        this.following.add(toBeFollowed);
    }

    public List<String> following() {
        return this.following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(following, user.following);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, following);
    }
}
