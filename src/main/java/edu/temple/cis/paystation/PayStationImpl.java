/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */

package edu.temple.cis.paystation;
import java.util.*;

public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    private int machineTotal;
    private Map<Integer, Integer> coinsInPS = new HashMap<>();

    @Override
    public void addPayment(int coinValue) throws IllegalCoinException {
        if (coinValue == 5 || coinValue == 10 || coinValue == 25) {
            checkForCoinInMap(coinValue);
            insertedSoFar += coinValue;
            timeBought = insertedSoFar / 5 * 2;
        } else {
            throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        machineTotal += insertedSoFar;
        reset();
        return r;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        Map<Integer, Integer> coinsReturned = new HashMap<>(coinsInPS);
        reset();
        return coinsReturned;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        coinsInPS.clear();
    }

    public int empty() {
        int temp = machineTotal;
        machineTotal = 0;
        return temp;
    }

    private void checkForCoinInMap (int Key) {
        // If map contains coin, add 1 to current total
        if (coinsInPS.containsKey(Key)) {
            coinsInPS.put(Key, coinsInPS.get(Key)+1);
        }

        // Otherwise, initialize the key with 1 coin
        else {
            coinsInPS.put(Key, 1);
        }
    }
}