package com.ms.service;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DiceServiceTest {

    @Test
    public void shouldReturnValidNumberForInputLimit() {
        DiceService diceService = new DiceService();
        int value = diceService.roll(6);
        assertTrue(value > 0 && value <= 6);
    }
}