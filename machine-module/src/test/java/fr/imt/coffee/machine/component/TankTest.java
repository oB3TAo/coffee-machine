package fr.imt.coffee.machine.component;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test
    void testConstructorWithValidValues() {
        Tank tank = new Tank(2.0, 1.0, 5.0);
        assertEquals(2.0, tank.getActualVolume());
        assertEquals(1.0, tank.getMinVolume());
        assertEquals(5.0, tank.getMaxVolume());
    }

    @Test
    void testConstructorWithInvalidInitialVolume() {
        assertThrows(IllegalArgumentException.class, () -> new Tank(-1.0, 1.0, 5.0),
                "Should throw exception for initial volume below zero.");
    }

    @Test
    void testConstructorWithMinVolumeGreaterThanMaxVolume() {
        assertThrows(IllegalArgumentException.class, () -> new Tank(2.0, 6.0, 5.0),
                "Should throw exception when minVolume is greater than maxVolume.");
    }

    @Test
    void testConstructorWithInitialVolumeExceedingMaxVolume() {
        assertThrows(IllegalArgumentException.class, () -> new Tank(6.0, 1.0, 5.0),
                "Should throw exception when initial volume exceeds max volume.");
    }

    @Test
    void testDecreaseVolumeInTank() {
        Tank tank = new Tank(3.0, 1.0, 5.0);
        tank.decreaseVolumeInTank(1.0);
        assertEquals(2.0, tank.getActualVolume(), "Actual volume should decrease by the specified amount.");
    }

    @Test
    void testDecreaseVolumeBelowMinVolume() {
        Tank tank = new Tank(1.5, 1.0, 5.0);
        assertThrows(IllegalArgumentException.class, () -> tank.decreaseVolumeInTank(1.0),
                "Should throw exception if the volume goes below the minimum allowed.");
    }

    @Test
    void testIncreaseVolumeInTank() {
        Tank tank = new Tank(2.0, 1.0, 5.0);
        tank.increaseVolumeInTank(1.0);
        assertEquals(3.0, tank.getActualVolume(), "Actual volume should increase by the specified amount.");
    }

    @Test
    void testIncreaseVolumeAboveMaxVolume() {
        Tank tank = new Tank(4.0, 1.0, 5.0);
        assertThrows(IllegalArgumentException.class, () -> tank.increaseVolumeInTank(2.0),
                "Should throw exception if the volume exceeds the maximum allowed.");
    }

    @Test
    void testEmptyTank() {
        Tank tank = new Tank(3.0, 0.0, 5.0);
        tank.emptyTank();
        assertEquals(0.0, tank.getActualVolume(), "Actual volume should be 0 after emptying the tank.");
    }

    @Test
    void testIsEmpty() {
        Tank tank = new Tank(0.0, 0.0, 5.0);
        assertTrue(tank.isEmpty(), "Tank should be empty when actual volume equals min volume.");

        Tank nonEmptyTank = new Tank(2.0, 0.0, 5.0);
        assertFalse(nonEmptyTank.isEmpty(), "Tank should not be empty when actual volume is above min volume.");
    }

    @Test
    void testIsFull() {
        Tank tank = new Tank(5.0, 0.0, 5.0);
        assertTrue(tank.isFull(), "Tank should be full when actual volume equals max volume.");

        Tank nonFullTank = new Tank(2.0, 0.0, 5.0);
        assertFalse(nonFullTank.isFull(), "Tank should not be full when actual volume is below max volume.");
    }

    @Test
    void testDecreaseVolumeWithNegativeValue() {
        Tank tank = new Tank(3.0, 1.0, 5.0);
        assertThrows(IllegalArgumentException.class, () -> tank.decreaseVolumeInTank(-1.0),
                "Should throw exception for negative volume decrease.");
    }

    @Test
    void testIncreaseVolumeWithNegativeValue() {
        Tank tank = new Tank(3.0, 1.0, 5.0);
        assertThrows(IllegalArgumentException.class, () -> tank.increaseVolumeInTank(-1.0),
                "Should throw exception for negative volume increase.");
    }
}
