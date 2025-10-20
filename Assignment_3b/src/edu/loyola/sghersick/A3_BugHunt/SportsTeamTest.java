package edu.loyola.sghersick.A3_BugHunt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Quick tests for SportsTeam enum
 * Making sure all the sports are available
 * 
 * @author Alex Johnson
 * @version 1.0
 */
public class SportsTeamTest {

    @Test
    public void testCommonSportsExist() {
        // Check that major sports are included
        assertNotNull(SportsTeam.BASKETBALL);
        assertNotNull(SportsTeam.SOCCER);
        assertNotNull(SportsTeam.TENNIS);
        assertNotNull(SportsTeam.SWIMMING);
    }

    @Test
    public void testAllSportsCount() {
        // Make sure we have all expected sports teams
        SportsTeam[] teams = SportsTeam.values();
        assertEquals(9, teams.length, "Should have 9 sports teams");
    }

    @Test
    public void testFindSpecificTeam() {
        // Test looking up a specific sport
        SportsTeam team = SportsTeam.valueOf("VOLLEYBALL");
        assertEquals(SportsTeam.VOLLEYBALL, team);
    }

    @Test
    public void testUseInStudent() {
        // Test that sports teams work with Student class
        Student athlete = new Student(2002, "Jamal Williams", SportsTeam.BASKETBALL);
        assertNotNull(athlete);
    }
}