package fr.imt.coffee.machine.component;

import fr.imt.coffee.storage.cupboard.coffee.type.CoffeeType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BeanTankTest {

    @Test
    void testIncreaseCoffeeVolumeInTankWithValidValues() {
        BeanTank beanTank = new BeanTank(1.0, 0.5, 5.0, CoffeeType.ARABICA);
        beanTank.increaseCoffeeVolumeInTank(1.0, CoffeeType.ROBUSTA);
        assertEquals(CoffeeType.ROBUSTA, beanTank.getBeanCoffeeType());
    }

    @Test
    void testIncreaseCoffeeVolumeInTankExceedingMaxVolume() {
        BeanTank beanTank = new BeanTank(4.0, 0.5, 5.0, CoffeeType.ARABICA);
        assertThrows(IllegalArgumentException.class, () ->
                beanTank.increaseCoffeeVolumeInTank(2.0, CoffeeType.ROBUSTA)
        );
    }

    @Test
    void testConstructorWithInvalidInitialVolume() {
        assertThrows(IllegalArgumentException.class, () ->
                new BeanTank(-1.0, 0.5, 5.0, CoffeeType.ARABICA)
        );
    }

    @Test
    void testConstructorWithMinGreaterThanMax() {
        assertThrows(IllegalArgumentException.class, () ->
                new BeanTank(1.0, 6.0, 5.0, CoffeeType.ARABICA)
        );
    }
}
