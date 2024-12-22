package fr.imt.coffee;

import fr.imt.coffee.machine.CoffeeMachine;
import fr.imt.coffee.storage.cupboard.coffee.type.CoffeeType;
import fr.imt.coffee.storage.cupboard.container.Cup;
import fr.imt.coffee.storage.cupboard.container.Mug;
import fr.imt.coffee.storage.cupboard.exception.CupNotEmptyException;
import fr.imt.coffee.machine.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CoffeeMachineUnitTest {
    public CoffeeMachine coffeeMachineUnderTest;

    @BeforeEach
    public void beforeTest(){
        coffeeMachineUnderTest = new CoffeeMachine(
                0, 10,
                0, 10, 700);
    }

    @Test
    public void testMachineFailureTrue(){
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(1.0);
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
        assertThat(false, is(coffeeMachineUnderTest.isOutOfOrder()));

        coffeeMachineUnderTest.coffeeMachineFailure();

        Assertions.assertTrue(coffeeMachineUnderTest.isOutOfOrder());
        assertThat(true, is(coffeeMachineUnderTest.isOutOfOrder()));
    }

    @Test
    public void testMachineFailureFalse(){
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
        assertThat(false, is(coffeeMachineUnderTest.isOutOfOrder()));

        coffeeMachineUnderTest.coffeeMachineFailure();

        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
        assertThat(false, is(coffeeMachineUnderTest.isOutOfOrder()));
    }

    @Test
    public void testPlugMachine(){
        Assertions.assertFalse(coffeeMachineUnderTest.isPlugged());

        coffeeMachineUnderTest.plugToElectricalPlug();

        Assertions.assertTrue(coffeeMachineUnderTest.isPlugged());
    }

    @Test
    public void testMakeACoffeeCupNotEmptyException() {
        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(false);

        coffeeMachineUnderTest.plugToElectricalPlug();

        Assertions.assertThrows(CupNotEmptyException.class, () -> {
            coffeeMachineUnderTest.makeACoffee(mockCup, CoffeeType.MOKA);
        });
    }

    @Test
    public void testMakeACoffeeSuccess() throws Exception {
        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addWaterInTank(2.0);
        coffeeMachineUnderTest.addCoffeeInBeanTank(5.0, CoffeeType.MOKA);

        coffeeMachineUnderTest.setRandomGenerator(new Random() {
            @Override
            public double nextGaussian() {
                return 0;
            }
        });

        Cup cup = new Cup(0.25);
        cup.setEmpty(true);

        var coffee = coffeeMachineUnderTest.makeACoffee(cup, CoffeeType.MOKA);

        Assertions.assertNotNull(coffee);
        Assertions.assertFalse(coffee.isEmpty());
        Assertions.assertEquals(CoffeeType.MOKA, coffee.getCoffeeType());
        Assertions.assertEquals(cup.getCapacity(), coffee.getCapacity(), 0.01);
    }


    @Test
    public void testMakeACoffeeLackOfWaterException() {
        coffeeMachineUnderTest.plugToElectricalPlug();

        Cup cup = new Cup(1.0);
        cup.setEmpty(true);

        Assertions.assertThrows(LackOfWaterInTankException.class, () -> {
            coffeeMachineUnderTest.makeACoffee(cup, CoffeeType.MOKA);
        });
    }

    @Test
    public void testMakeACoffeeMachineNotPluggedException() {
        Cup cup = new Cup(1.0);
        cup.setEmpty(true);

        Assertions.assertThrows(MachineNotPluggedException.class, () -> {
            coffeeMachineUnderTest.makeACoffee(cup, CoffeeType.MOKA);
        });
    }

    @Test
    public void testMakeACoffeeEmptyBeanTankException() {
        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addWaterInTank(2.0);

        Cup cup = new Cup(1.0);
        cup.setEmpty(true);

        Assertions.assertThrows(EmptyBeanTankException.class, () -> {
            coffeeMachineUnderTest.makeACoffee(cup, CoffeeType.MOKA);
        });
    }

    @Test
    public void testMakeACoffeeTypeMismatchException() throws Exception {
        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addWaterInTank(2.0);
        coffeeMachineUnderTest.addCoffeeInBeanTank(10.0, CoffeeType.ARABICA);

        Cup cup = new Cup(1.0);
        cup.setEmpty(true);

        Assertions.assertThrows(CoffeeTypeCupDifferentOfCoffeeTypeTankException.class, () -> {
            coffeeMachineUnderTest.makeACoffee(cup, CoffeeType.MOKA);
        });
    }

    @AfterEach
    public void afterTest(){
        coffeeMachineUnderTest = null;
    }
}
