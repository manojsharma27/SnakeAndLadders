package com.ms.service;

import java.util.Random;

public class DiceService {

    private Random random;

    public DiceService() {
        random = new Random();
    }

    public int roll(int limit) {
        return random.nextInt(limit) + 1;
    }
}
