package upsd.model.post;

import upsd.model.user.User;

import java.time.LocalDateTime;

public class Post {

    private final User user;
    private final String message;
    private LocalDateTime timePosted;

    public Post(User user, String message, LocalDateTime timePosted) {
        this.user = user;
        this.message = message;
        this.timePosted = timePosted;
    }

    public User user() {
        return this.user;
    }

    public String message() {
        return this.message;
    }

    public LocalDateTime timePosted() {
        return this.timePosted;
    }
}
