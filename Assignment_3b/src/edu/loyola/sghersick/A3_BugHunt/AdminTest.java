package edu.loyola.sghersick.A3_BugHunt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    private Admin admin;
    private Student s1, s2, s3;
    private ArrayList<Student> students;

    private static final double HOURS_PER_WEEK = 8.0;
    private static final double MAX_DAY_HOURS = 4.0;
    private static final LocalDate DEADLINE = LocalDate.of(2025, 10, 9);

    @BeforeEach
    void setUp() {
        s1 = new Student(202717, "Sam Hersick", SportsTeam.SWIMMING);
        s2 = new Student(219753, "Tom McCarthy", SportsTeam.SOCCER);
        s3 = new Student(263254, "John Smith", SportsTeam.TENNIS);

        students = new ArrayList<>(Arrays.asList(s1, s2, s3));
        admin = new Admin(students, HOURS_PER_WEEK, MAX_DAY_HOURS);

        // Add valid sessions
        // Sam completes full week requirement (8 hours)
        admin.addSession(202717, new Session(
                LocalDateTime.of(2025, 10, 3, 9, 0),
                LocalDateTime.of(2025, 10, 3, 13, 0) // 4 hours
        ));
        admin.addSession(202717, new Session(
                LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 14, 0) // 4 hours
        ));

        // Tom partial hours (5 hours)
        admin.addSession(219753, new Session(
                LocalDateTime.of(2025, 10, 3, 10, 0),
                LocalDateTime.of(2025, 10, 3, 12, 0) // 2 hours
        ));
        admin.addSession(219753, new Session(
                LocalDateTime.of(2025, 10, 4, 13, 0),
                LocalDateTime.of(2025, 10, 4, 16, 0) // 3 hours
        ));

        // John partial hours (3 hours)
        admin.addSession(263254, new Session(
                LocalDateTime.of(2025, 10, 5, 9, 0),
                LocalDateTime.of(2025, 10, 5, 12, 0) // 3 hours
        ));
    }

    @Test
    void testAddSessionValidStudent() {
        double[] hours = s1.calculateWeekHours(DEADLINE, HOURS_PER_WEEK, MAX_DAY_HOURS);
        assertEquals(8.0, hours[0], 0.001, "Sam should have completed 8 hours");
    }

    @Test
    void testAddSessionInvalidStudent() {
        Session session = new Session(
                LocalDateTime.of(2025, 10, 3, 9, 0),
                LocalDateTime.of(2025, 10, 3, 11, 0)
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            admin.addSession(999999, session);
        });

        assertTrue(exception.getMessage().contains("Student with ID 999999 not found"));
    }

    @Test
    void testGetSlackers() {
        HashMap<Student, double[]> slackers = admin.getSlackers(DEADLINE);

        // Sam should NOT be slacker
        assertFalse(slackers.containsKey(s1), "Sam should not be flagged as slacker");

        // Tom and John should be slackers
        assertTrue(slackers.containsKey(s2), "Tom should be flagged as slacker");
        assertEquals(5.0, slackers.get(s2)[0], 0.001, "Tom's completed hours = 5");

        assertTrue(slackers.containsKey(s3), "John should be flagged as slacker");
        assertEquals(3.0, slackers.get(s3)[0], 0.001, "John's completed hours = 3");
    }
}
