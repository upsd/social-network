package upsd.system_interfaces;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClockShould {

    @Test
    public void calculateTimeDifferenceCorrectly() {
        Clock clock = mock(Clock.class);
        LocalDateTime timeOfDoingSomething = LocalDateTime.of(2018, 06, 28, 8, 15);
        LocalDateTime fifteenMinutesLater = LocalDateTime.of(2018, 06, 28, 8, 30);

        given(clock.now()).willReturn(fifteenMinutesLater);
        when(clock.calculateTimeDifferenceInMinutes(timeOfDoingSomething)).thenCallRealMethod();

        int timeDifferenceInMinutes = clock.calculateTimeDifferenceInMinutes(timeOfDoingSomething);

        assertEquals(15, timeDifferenceInMinutes);
    }
}
