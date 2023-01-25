package com.example.boot.entities;

import java.util.ArrayList;
import java.util.Random;

public class LoginThread extends Thread {
    Random rand = new Random();
    ArrayList<ArrayList<Integer>> arrayOfArraysOfIntegers = new ArrayList<>();

    @Override
    public void run() {
        while (true) {
            ArrayList<Integer> list = new ArrayList<>();
            try {
                while (true) {
                    list.add(rand.nextInt(999));
                }
            } catch (OutOfMemoryError e) {
                arrayOfArraysOfIntegers.add(list);
                System.out.println("new integer array added to the array of arrays");
            }
        }
    }
}