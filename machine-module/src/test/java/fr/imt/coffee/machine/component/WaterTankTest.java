package fr.imt.coffee.machine.component;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WaterTankTest {

    @Test
    void testConstructorWithValidValues() {
        WaterTank waterTank = new WaterTank(2.0, 1.0, 5.0);
        assertEquals(2.0, waterTank.getActualVolume());
        assertEquals(1.0, waterTank.getMinVolume());
        assertEquals(5.0, waterTank.getMaxVolume());
    }

    @Test
    void testConstructorWithInvalidInitialVolume() {
        assertThrows(IllegalArgumentException.class, () -> new WaterTank(-1.0, 1.0, 5.0),
                "Should throw exception for initial volume below zero.");
    }

    @Test
    void testDecreaseVolumeBelowMinVolume() {
        WaterTank waterTank = new WaterTank(1.5, 1.0, 5.0);
        assertThrows(IllegalArgumentException.class, () -> waterTank.decreaseVolumeInTank(1.0),
                "Should throw exception if the volume goes below the minimum allowed.");
    }

    @Test
    void testIncreaseVolumeAboveMaxVolume() {
        WaterTank waterTank = new WaterTank(4.0, 1.0, 5.0);
        assertThrows(IllegalArgumentException.class, () -> waterTank.increaseVolumeInTank(2.0),
                "Should throw exception if the volume exceeds the maximum allowed.");
    }
}

