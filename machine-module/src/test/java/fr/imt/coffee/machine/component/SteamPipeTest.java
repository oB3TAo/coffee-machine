package fr.imt.coffee.machine.component;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SteamPipeTest {

    @Test
    void testConstructorInitialState() {
        SteamPipe steamPipe = new SteamPipe();
        assertFalse(steamPipe.isOn(), "SteamPipe should be off by default.");
    }

    @Test
    void testSetOn() {
        SteamPipe steamPipe = new SteamPipe();
        steamPipe.setOn();
        assertTrue(steamPipe.isOn(), "SteamPipe should be on after calling setOn.");
    }

    @Test
    void testSetOff() {
        SteamPipe steamPipe = new SteamPipe();
        steamPipe.setOn(); // Turn it on first
        steamPipe.setOff();
        assertFalse(steamPipe.isOn(), "SteamPipe should be off after calling setOff.");
    }
}
