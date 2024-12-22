package fr.imt.coffee.cucumber.steps;

import fr.imt.coffee.machine.CoffeeMachine;
import fr.imt.coffee.machine.exception.*;
import fr.imt.coffee.storage.cupboard.coffee.type.CoffeeType;
import fr.imt.coffee.storage.cupboard.container.*;
import fr.imt.coffee.storage.cupboard.exception.CupNotEmptyException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class CucumberStepsCoffeeMachineMakeACoffeeTest {

    public CoffeeMachine coffeeMachine;
    public Mug mug;
    public Cup cup;
    public CoffeeContainer containerWithCoffee;
    public Exception capturedException;

    @Given("a coffee machine with {double} l of min capacity, {double} l of max capacity, and {double} l per hour of water flow for the pump")
    public void givenACoffeeMachine(double minimalWaterCapacity, double maximalWaterCapacity, double pumpWaterFlow) {
        coffeeMachine = new CoffeeMachine(minimalWaterCapacity, maximalWaterCapacity, minimalWaterCapacity, maximalWaterCapacity, pumpWaterFlow);
    }

    @And("a {string} with a capacity of {double} liters")
    public void aWithACapacityOf(String containerType, double containerCapacity) {
        if ("mug".equals(containerType))
            mug = new Mug(containerCapacity);
        if ("cup".equals(containerType))
            cup = new Cup(containerCapacity);
    }

    @And("the {string} is not empty")
    public void theContainerIsNotEmpty(String containerType) {
        if ("mug".equals(containerType))
            mug.setEmpty(false);
        if ("cup".equals(containerType))
            cup.setEmpty(false);
    }

    @When("I plug the machine to electricity")
    public void iPlugTheMachineToElectricity() {
        coffeeMachine.plugToElectricalPlug();
    }

    @And("I add {double} liter of water in the water tank")
    public void iAddLitersOfWater(double waterVolume) {
        coffeeMachine.addWaterInTank(waterVolume);
    }

    @And("I add {double} liter of {string} in the bean tank")
    public void iAddLitersOfCoffeeBeans(double beanVolume, String coffeeType) {
        coffeeMachine.addCoffeeInBeanTank(beanVolume, CoffeeType.valueOf(coffeeType));
    }

    @And("I make a coffee {string}")
    public void iMakeACoffee(String coffeeType) {
        try {
            Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
            Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
            coffeeMachine.setRandomGenerator(randomMock);

            if (mug != null)
                containerWithCoffee = coffeeMachine.makeACoffee(mug, CoffeeType.valueOf(coffeeType));
            if (cup != null)
                containerWithCoffee = coffeeMachine.makeACoffee(cup, CoffeeType.valueOf(coffeeType));
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("the coffee machine returns a coffee {string} not empty")
    public void theCoffeeMachineReturnsACoffeeContainerNotEmpty(String containerType) {
        Assertions.assertFalse(containerWithCoffee.isEmpty());
        if ("mug".equals(containerType))
            assertThat(containerWithCoffee, instanceOf(CoffeeMug.class));
        if ("cup".equals(containerType))
            assertThat(containerWithCoffee, instanceOf(CoffeeCup.class));
    }

    @And("the coffee volume equals to {double} liters")
    public void theCoffeeVolumeEqualsTo(double coffeeVolume) {
        assertThat(coffeeVolume, is(containerWithCoffee.getCapacity()));
    }

    @And("the coffee {string} contains a coffee type {string}")
    public void theCoffeeContainerContainsACoffeeType(String containerType, String coffeeType) {
        if ("mug".equals(containerType))
            assertThat(containerWithCoffee, instanceOf(CoffeeMug.class));
        if ("cup".equals(containerType))
            assertThat(containerWithCoffee, instanceOf(CoffeeCup.class));

        assertThat(containerWithCoffee.getCoffeeType(), is(CoffeeType.valueOf(coffeeType)));
    }

    @Then("an error {string} is thrown")
    public void anErrorIsThrown(String errorMessage) {
        Assertions.assertNotNull(capturedException, "No exception was thrown.");
        Assertions.assertEquals(errorMessage, capturedException.getMessage(),
                "Expected error message does not match the actual message.");
    }
}
