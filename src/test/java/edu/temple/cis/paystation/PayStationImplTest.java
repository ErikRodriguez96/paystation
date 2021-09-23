/*
  Testcases for the Pay Station system.

  This source code is from the book "Flexible, Reliable Software: Using
  Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
  Christensen Computer Science Department Aarhus University

  This source code is provided WITHOUT ANY WARRANTY either expressed or
  implied. You may study, use, modify, and distribute it for non-commercial
  purposes. For any commercial use, see http://www.baerbak.com/
 */
package edu.temple.cis.paystation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.*;

public class PayStationImplTest {

    PayStation ps;

    @Before
    public void setup() {
        ps = new PayStationImpl();
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents() throws IllegalCoinException {
        ps.addPayment(5);

        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);

        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents() throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);

        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy() throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();

        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c() throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();

        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy() throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0

        assertEquals("Display should have been cleared",
                0, ps.readDisplay());

        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);

        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());

        Receipt r = ps.buy();

        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    @Test
    public void shouldClearAfterCancel() throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();

        assertEquals("Cancel should clear display",
                0, ps.readDisplay());

        ps.addPayment(25);

        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }

    /**
     * Verify that calling empty returns coins after calling buy
     */
    @Test
    public void shouldReturnCoinAfterEmpty() throws IllegalCoinException {
        ps.addPayment(5);

        assertEquals("Should display 2 minutes",
                2, ps.readDisplay());

        ps.buy();

        assertEquals("After buy should display 0 minutes",
                0, ps.readDisplay());
        assertEquals("Machine should contain 5 cents",
                5, ps.empty());
    }

    /**
     * Verify that calling empty returns 0 without first calling buy
     */
    @Test
    public void shouldReturn0AfterEmptyWithoutBuy() throws IllegalCoinException {
        ps.addPayment(5);

        assertEquals("Should display 2 minutes",
                2, ps.readDisplay());
        assertEquals("Without buy should contain 0 cents",
                0, ps.empty());
    }

    /**
     * Verify that second consecutive call to empty returns 0 coins
     */
    @Test
    public void shouldReturn0AfterSecondEmpty() throws IllegalCoinException {
        ps.addPayment(5);
        ps.buy();

        assertEquals("After buy should contain 5 cents",
                5, ps.empty());
        assertEquals("Second empty should contain 0 cents",
                0, ps.empty());
    }

    /**
     * Verify that calling cancel returns a map with only the coin added via addPayment
     */
    @Test
    public void shouldReturnMapWith1Coin() throws IllegalCoinException {
        ps.addPayment(5);

        Map<Integer, Integer> coins = ps.cancel();

        assertEquals("Map should contain 1 key",
                1, coins.entrySet().size());
    }

    /**
     * Verify that calling cancel returns a map with only the coins added via addPayment
     */
    @Test
    public void shouldReturnMapWithMixedCoins() throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(5);

        Map<Integer, Integer> coins = ps.cancel();

        assertNotNull("Should contain nickels", coins.get(5));
        assertNotNull("Should contain dimes", coins.get(10));
        assertNull("Should not contain quarters", coins.get(25));
    }

    /**
     * Verify that cancel returns a map with no coins if addPayment was not called
     */
    @Test
    public void shouldReturnEmptyMap() {
        Map<Integer, Integer> coins = ps.cancel();

        assertEquals("Map should be empty",
                0, coins.entrySet().size());
    }

    /**
     * Verify that the second call to cancel contains an empty map
     */
    @Test
    public void shouldClearMapAfterSecondCancel() throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(25);

        assertEquals("Cancel should return map with coins",
                2, ps.cancel().size());
        assertEquals("Second call to cancel should return an empty map",
                0, ps.cancel().size());
    }

    /**
     * Verify that second buy without new coins returns receipt with 0 minutes of parking time
     */
    @Test
    public void shouldClearMapAfterBuy() throws IllegalCoinException {
        ps.addPayment(10);

        assertEquals("Buy should return receipt with 4 minutes",
                4, ps.buy().value());
        assertEquals("Second buy should return receipt with 0 minutes",
                0, ps.buy().value());
    }

}