package fr.imt.coffee.machine.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WaterPumpTest {

    @Test
    void testPumpWaterSuccessfully() throws InterruptedException {
        WaterTank mockWaterTank = mock(WaterTank.class);
        WaterPump waterPump = new WaterPump(2.0);

        double pumpingTime = waterPump.pumpWater(4.0, mockWaterTank);

        verify(mockWaterTank, times(1)).decreaseVolumeInTank(4.0);
        assertEquals(4000, pumpingTime, "Pumping time should match the expected value (ms).");
    }

    @Test
    void testPumpWaterWithInterruptedException() {
        WaterTank mockWaterTank = mock(WaterTank.class);
        WaterPump waterPump = new WaterPump(2.0);

        Thread.currentThread().interrupt();
        assertThrows(InterruptedException.class, () -> waterPump.pumpWater(4.0, mockWaterTank),
                "Should throw InterruptedException if the thread is interrupted during pumping.");
    }

    @Test
    void testPumpWaterWithZeroPumpingCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new WaterPump(0.0),
                "Should throw exception for zero pumping capacity.");
    }

    @Test
    void testPumpWaterWithNegativePumpingCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new WaterPump(-1.0),
                "Should throw exception for negative pumping capacity.");
    }

    @Test
    void testPumpWaterWithZeroWaterVolume() {
        WaterPump waterPump = new WaterPump(2.0);
        WaterTank mockWaterTank = mock(WaterTank.class);

        assertThrows(IllegalArgumentException.class, () -> waterPump.pumpWater(0.0, mockWaterTank),
                "Should throw exception for zero water volume.");
    }

    @Test
    void testPumpWaterWithNegativeWaterVolume() {
        WaterPump waterPump = new WaterPump(2.0);
        WaterTank mockWaterTank = mock(WaterTank.class);

        assertThrows(IllegalArgumentException.class, () -> waterPump.pumpWater(-1.0, mockWaterTank),
                "Should throw exception for negative water volume.");
    }

    @Test
    void testPumpWaterWithExtremePumpingTime() throws InterruptedException {
        WaterTank mockWaterTank = mock(WaterTank.class);
        WaterPump waterPump = new WaterPump(1.0);

        double pumpingTime = waterPump.pumpWater(0.001, mockWaterTank);

        verify(mockWaterTank, times(1)).decreaseVolumeInTank(0.001);
        assertEquals(2, pumpingTime, "Pumping time should match the expected value for tiny water volume.");
    }

    @Test
    void testGetPumpingCapacity() {
        WaterPump waterPump = new WaterPump(2.5);
        assertEquals(2.5, waterPump.getPumpingCapacity(),
                "Pumping capacity should match the value provided at creation.");
    }
}
