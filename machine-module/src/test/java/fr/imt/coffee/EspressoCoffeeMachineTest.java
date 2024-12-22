package fr.imt.coffee;

import fr.imt.coffee.machine.component.BeanTank;
import fr.imt.coffee.machine.EspressoCoffeeMachine;
import fr.imt.coffee.machine.component.Tank;
import fr.imt.coffee.storage.cupboard.container.CoffeeCup;
import fr.imt.coffee.storage.cupboard.container.Cup;
import fr.imt.coffee.storage.cupboard.coffee.type.CoffeeType;
import fr.imt.coffee.machine.exception.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EspressoCoffeeMachineTest {

    private EspressoCoffeeMachine espressoMachine;

    @Before
    public void setup() {
        espressoMachine = new EspressoCoffeeMachine(0, 10, 0, 10, 700);
        espressoMachine.plugToElectricalPlug();
        espressoMachine.addWaterInTank(1.0);
        espressoMachine.addCoffeeInBeanTank(0.5, CoffeeType.ARABICA);
    }

    @Test
    public void testMakeCoffee_Successful() throws Exception {
        Cup cup = new Cup(0.2);
        CoffeeCup coffeeCup = new CoffeeCup(cup, CoffeeType.ARABICA);
        coffeeCup.setEmpty(true);

        CoffeeCup result = (CoffeeCup) espressoMachine.makeACoffee(coffeeCup, CoffeeType.ARABICA);

        assertNotNull("The result should not be null", result);
        assertEquals("The coffee type should be ARABICA", CoffeeType.ARABICA, result.getCoffeeType());
        //assertFalse("The cup should not be empty", result.isEmpty());
    }

//    @Test(expected = MachineNotPluggedException.class)
//    public void testMakeCoffee_MachineNotPlugged() throws Exception {
//        espressoMachine.reset();
//        Cup cup = new Cup(0.2);
//        cup.setEmpty(true); // Ensure the cup is empty.
//        CoffeeCup coffeeCup = new CoffeeCup(cup, CoffeeType.ARABICA);
//
//        espressoMachine.makeACoffee(coffeeCup, CoffeeType.ARABICA);
//    }


    @Test(expected = LackOfWaterInTankException.class)
    public void testMakeCoffee_InsufficientWater() throws Exception {
        espressoMachine.getWaterTank().emptyTank();
        espressoMachine.getWaterTank().increaseVolumeInTank(0.1);
        Cup cup = new Cup(0.2);
        CoffeeCup coffeeCup = new CoffeeCup(cup, CoffeeType.ARABICA);
        coffeeCup.setEmpty(true);

        espressoMachine.makeACoffee(coffeeCup, CoffeeType.ARABICA);
    }

    @Test(expected = CoffeeTypeCupDifferentOfCoffeeTypeTankException.class)
    public void testMakeCoffee_MismatchedCoffeeType() throws Exception {
        Cup cup = new Cup(0.2);
        CoffeeCup coffeeCup = new CoffeeCup(cup, CoffeeType.ROBUSTA);
        coffeeCup.setEmpty(true);

        espressoMachine.makeACoffee(coffeeCup, CoffeeType.ROBUSTA);
    }

//    @Test
//    public void testMakeCoffee_FailureSimulation() {
//        espressoMachine.setOutOfOrder(true);
//        Cup cup = new Cup(0.2);
//        CoffeeCup coffeeCup = new CoffeeCup(cup, CoffeeType.ARABICA);
//        coffeeCup.setEmpty(true);
//
//        Exception exception = null;
//        try {
//            espressoMachine.makeACoffee(coffeeCup, CoffeeType.ARABICA);
//        } catch (Exception e) {
//            exception = e;
//        }
//
//        assertNotNull("An exception should have been thrown", exception);
//        assertTrue("Exception should be MachineOutOfOrderException", exception instanceof OutOfOrderException);
//    }

    @Test
    public void testWaterTankBehavior() {
        Tank waterTank = espressoMachine.getWaterTank();
        assertFalse("The water tank should not be empty initially", waterTank.isEmpty());
        assertTrue("The water tank should not be full initially", waterTank.getActualVolume() < waterTank.getMaxVolume());

        waterTank.increaseVolumeInTank(waterTank.getMaxVolume() - waterTank.getActualVolume());
        assertTrue("The water tank should now be full", waterTank.isFull());
    }

    @Test
    public void testSecondaryBeanTank() {
        BeanTank secondaryBeanTank = espressoMachine.getBeanTank();

        secondaryBeanTank.increaseCoffeeVolumeInTank(0.2, CoffeeType.ROBUSTA);

        assertEquals("The secondary bean tank should contain ROBUSTA coffee", CoffeeType.ROBUSTA, secondaryBeanTank.getBeanCoffeeType());
    }
}
