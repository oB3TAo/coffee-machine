package fr.imt.coffee.machine.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoffeeGrinderTest {

    @Test
    void testGrindCoffeeSuccessfully() throws InterruptedException {
        BeanTank mockBeanTank = mock(BeanTank.class);
        CoffeeGrinder coffeeGrinder = new CoffeeGrinder(2000);

        double grindingTime = coffeeGrinder.grindCoffee(mockBeanTank);

        verify(mockBeanTank, times(1)).increaseVolumeInTank(0.2);
        assertEquals(2000, grindingTime);
    }

    @Test
    void testGrindCoffeeWithInterruptedException() {
        BeanTank mockBeanTank = mock(BeanTank.class);
        CoffeeGrinder coffeeGrinder = new CoffeeGrinder(2000);

        Thread.currentThread().interrupt(); // Force interruption
        assertThrows(InterruptedException.class, () -> coffeeGrinder.grindCoffee(mockBeanTank));
    }
}

