package fr.imt.coffee.cucumber.steps;

import fr.imt.coffee.machine.EspressoCoffeeMachine;
import fr.imt.coffee.machine.exception.CoffeeTypeCupDifferentOfCoffeeTypeTankException;
import fr.imt.coffee.machine.exception.LackOfWaterInTankException;
import fr.imt.coffee.machine.exception.MachineNotPluggedException;
import fr.imt.coffee.machine.exception.OutOfOrderException;
import fr.imt.coffee.storage.cupboard.container.CoffeeCup;
import fr.imt.coffee.storage.cupboard.container.Cup;
import fr.imt.coffee.storage.cupboard.coffee.type.CoffeeType;
import fr.imt.coffee.storage.cupboard.exception.CupNotEmptyException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import static org.junit.Assert.*;

public class CucumberStepsExpressoCoffeeMachineTest {

    private EspressoCoffeeMachine coffeeMachine;
    private CoffeeCup coffeeCup;
    private CoffeeType selectedCoffeeType;
    private boolean exceptionThrown;
    private String exceptionMessage;

    @Given("an espresso coffee machine with water and coffee beans")
    public void setupCoffeeMachine() {
        coffeeMachine = new EspressoCoffeeMachine(0, 10, 0, 10, 700);
        coffeeMachine.plugToElectricalPlug();
        coffeeMachine.addWaterInTank(1.0);
        coffeeMachine.addCoffeeInBeanTank(0.5, CoffeeType.ARABICA);
    }

    @And("an empty coffee cup with capacity {double}")
    public void setupCoffeeCup(double capacity) {
        coffeeCup = new CoffeeCup(new Cup(capacity), null);
        coffeeCup.setEmpty(true);
    }

    @When("I select the coffee type {string}")
    public void selectCoffeeType(String coffeeType) {
        selectedCoffeeType = CoffeeType.valueOf(coffeeType);
    }

    @And("I make a coffee")
    public void makeCoffee() {
        try {
            coffeeCup = (CoffeeCup) coffeeMachine.makeACoffee(new Cup(0.25), selectedCoffeeType); // Adjusted for proper casting
            exceptionThrown = false;
        } catch (LackOfWaterInTankException e) {
            exceptionThrown = true;
            exceptionMessage = "You must add more water in the water tank.";
        } catch (MachineNotPluggedException e) {
            exceptionThrown = true;
            exceptionMessage = "You must plug your coffee machine.";
        } catch (CupNotEmptyException e) {
            exceptionThrown = true;
            exceptionMessage = "The container given is not empty.";
        } catch (OutOfOrderException e) {
            exceptionThrown = true;
            exceptionMessage = "The coffee machine is out of order. Please reset it.";
        } catch (CoffeeTypeCupDifferentOfCoffeeTypeTankException e) {
            exceptionThrown = true;
            exceptionMessage = "The type of coffee to be made in the cup is different from that in the tank.";
        } catch (Exception e) {
            exceptionThrown = true;
            exceptionMessage = e.getMessage();
        }
    }


    @Then("the coffee cup should contain coffee of type {string}")
    public void verifyCoffee(String expectedCoffeeType) {
        assertFalse("An exception was thrown: " + exceptionMessage, exceptionThrown);
        assertNotNull("Cup is null", coffeeCup);
        assertEquals("Coffee type mismatch", CoffeeType.valueOf(expectedCoffeeType), coffeeCup.getCoffeeType());
        assertFalse("Cup should not be empty", coffeeCup.isEmpty());
    }

    @Then("an error should be thrown with message {string}")
    public void verifyErrorMessage(String expectedMessage) {
        assertTrue("No exception was thrown", exceptionThrown);
        assertEquals("Exception message mismatch", expectedMessage, exceptionMessage);
    }

    @And("the machine is not plugged in")
    public void theMachineIsNotPluggedIn() {
        coffeeMachine.reset();
    }

    @Given("the water tank has only {double} liters")
    public void theWaterTankHasOnlyLiters(double waterVolume) {
        coffeeMachine.getWaterTank().emptyTank();
        coffeeMachine.getWaterTank().increaseVolumeInTank(waterVolume);
    }

    @And("a non-empty coffee cup with capacity {double}")
    public void aNonEmptyCoffeeCupWithCapacity(double capacity) {
        Cup cup = new Cup(capacity);
        coffeeCup = new CoffeeCup(cup, CoffeeType.ARABICA); // Example with ARABICA
        coffeeCup.setEmpty(false); // Explicitly mark the cup as non-empty
    }

}
