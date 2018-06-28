package upsd.system_interfaces;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Clock {

    public int calculateTimeDifferenceInMinutes(LocalDateTime time) {
        long between = ChronoUnit.MINUTES.between(time, now());
        return (int) between;
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
