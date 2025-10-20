package edu.loyola.sghersick.A3_BugHunt;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {

    private Student student;
    private Session s1, s2, s3;
    private LocalDate deadline;

    @BeforeEach
    void setUp() {
        s1 = new Session(LocalDateTime.of(2025, 10, 3, 1, 0),
                         LocalDateTime.of(2025, 10, 3, 3, 0)); // 2 hrs
        s2 = new Session(LocalDateTime.of(2025, 10, 4, 2, 0),
                         LocalDateTime.of(2025, 10, 4, 6, 0)); // 4 hrs
        s3 = new Session(LocalDateTime.of(2025, 10, 7, 1, 0),
                         LocalDateTime.of(2025, 10, 7, 5, 0)); // 4 hrs

        student = new Student(1001, "Test Student", SportsTeam.SWIMMING);
        student.addSession(s1);
        student.addSession(s2);

        deadline = LocalDate.of(2025, 10, 7);
    }

    @Test
    void testIsId() {
        assertTrue(student.isId(1001));
        assertFalse(student.isId(9999));
    }

    @Test
    void testAddSession() {
        student.addSession(s3);
        double[] hours = student.calculateWeekHours(deadline, 20.0, 4.0);
        assertTrue(hours[0] > 0, "Week hours should be positive after adding session");
    }

    @Test
    void testCalculateWeekHoursWithBanked() {
        student.addSession(s3);
        double[] hours = student.calculateWeekHours(deadline, 8.0, 4.0);

        assertEquals(8.0, hours[0], "Week hours should be capped at weekly requirement");
        assertEquals(2.0, hours[1], "Banked hours should include excess above weekly requirement");
    }

    @Test
    void testToString() {
        String str = student.toString();
        assertTrue(str.contains("1001"), "Should contain student ID");
        assertTrue(str.contains("Test Student"), "Should contain student name");
    }
}
