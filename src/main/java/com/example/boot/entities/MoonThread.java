package com.example.boot.entities;

import java.util.ArrayList;
import java.util.Random;

public class MoonThread extends Thread {
    @Override
    public void run() {
        ArrayList<Integer> list = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 100000; i++) {
            int numberToAddToArray = rand.nextInt(1000000);
            list.add(numberToAddToArray);
        }
        
    }
}
