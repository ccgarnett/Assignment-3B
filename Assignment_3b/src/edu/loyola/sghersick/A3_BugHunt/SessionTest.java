package edu.loyola.sghersick.A3_BugHunt;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class SessionTest {

    @Test
    void testValidSession() {
        Session s = new Session(
                LocalDateTime.of(2025, 10, 3, 1, 0),
                LocalDateTime.of(2025, 10, 3, 3, 0)
        );

        assertEquals(2.0, s.calcDuration(), "Duration should be 2 hours");
        assertEquals(LocalDate.of(2025, 10, 3), s.getDate(), "Date should match checkOut date");

        String str = s.toString();
        assertTrue(str.contains("CheckIn"));
        assertTrue(str.contains("CheckOut"));
    }

    @Test
    void testInvalidSessionThrows() {
        // Check-out before check-in
        assertThrows(IllegalArgumentException.class, () -> 
            new Session(LocalDateTime.of(2025, 10, 3, 5, 0),
                        LocalDateTime.of(2025, 10, 3, 3, 0))
        );

        // Cross-day session
        assertThrows(IllegalArgumentException.class, () -> 
            new Session(LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 1, 0))
        );

        // Null session times
        assertThrows(IllegalArgumentException.class, () -> 
            new Session(null, LocalDateTime.of(2025, 10, 3, 1, 0))
        );
    }
}
