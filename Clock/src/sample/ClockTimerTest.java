package sample;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClockTimerTest {

    @Test
    public void testGetSeconds(){
        int expected = 20;
        ClockTimer clockTimer = new ClockTimer(5000);
        assertEquals(clockTimer.getSeconds(), expected);
    }

    @Test
    public void testGetMinut(){
        int expected = 23;
        ClockTimer clockTimer = new ClockTimer(5000);
        assertEquals(clockTimer.getMinutes(), expected);
    }

    @Test
    public void testGetHours(){
        int expected = 1;
        ClockTimer clockTimer = new ClockTimer(5000);
        assertEquals(clockTimer.getHours(), expected);
    }

    @Test
    public void testAddSeconds(){
        int expected = 0;
        ClockTimer clockTimer = new ClockTimer(12 * 60 * 60 - 1);
        clockTimer.addSecond();
        assertEquals(clockTimer.getHours(), expected);
        assertEquals(clockTimer.getMinutes(), expected);
        assertEquals(clockTimer.getSeconds(), expected);

    }

}