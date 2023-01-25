package com.example.boot.entities;

import java.util.ArrayList;
import java.util.Random;

public class LoginThread extends Thread {
    @Override
    public void run() {
        ArrayList<Integer> list = new ArrayList<>();
        Random rand = new Random();
        try {
            while (true) {
                list.add(rand.nextInt(999));
            }
        } catch (OutOfMemoryError e) {
        }
    }
}
