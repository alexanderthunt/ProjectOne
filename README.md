# Broken Planetarium

# Break
This planetarium will have a memory leak that will max out the JVM heap memory, it will keep the heap full even if there is garbage collection going on. The CPU will be under it's maximum load while the JVM heap is full. The result - requests will have longer and longer response times as the memory fills up and the CPU is put under heavier and heavier load. Eventually, all requests will return 504 timed out exception.

# Visual
## Broken
### New Custom Java Class responsible for creating the memory leak
```java
package com.example.boot.entities;

import java.util.ArrayList;
import java.util.Random;
/*
    this thread class populates an array with a lot of integers and puts it into an array of arrays, and continues doing that forever. 
    Taking up huge ammounts of memory.
*/
public class MemoryThread extends Thread {
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
            }
        }
    }
}
```
### Inserting the broken memory leak into the program in the PlanetController.java
```java
    /*
    I chose to insert the memory leak when a user tries to get a planet by it's name, but it can be inserted anywhere in the program.
    */
    MemoryThread arrayMaker = new MemoryThread();
    boolean MemoryLeakNotSimulatedYet = true;
    @GetMapping("/api/planet/{name}")
    public ResponseEntity<Planet> findByPlanetName(@PathVariable String name) {
        if (MemoryLeakNotSimulatedYet) {
            MemoryLeakNotSimulatedYet = false;
            /*
            It's important that arrayMaker.start() is only called once. Once it is called the first time, it never stops. 
            Calling start on a thread that is still running will cause an exception/500 reponse code.
            This will reveal the location the memory leak has been injected into the program.
            */
            arrayMaker.start();
        }
        return new ResponseEntity<>(this.planetService.findByPlanetName(name), HttpStatus.OK);
    }
```
## Fixed
```java
    //Simply do not call the thread with the memory leak.
    @GetMapping("/api/planet/{name}")
    public ResponseEntity<Planet> findByPlanetName(@PathVariable String name) {
        return new ResponseEntity<>(this.planetService.findByPlanetName(name), HttpStatus.OK);
    }
```
# Indicator
The most obvious indicator is that all requests will be over 200ms and eventually return 504 connection timed out response codes after the memory leak.
The Indicator for the root cause of the problem is in these preconfigured dashboards given by prometheus.
Kubernetes/Compute Resources/Cluser
Kubernetes/Compute Resources/Namespace(pods)
Kubernetes/Compute Resources/Namespace(Workloads)
I've attached screenshots of normal looking dashboards, and dashboards while the memory leak is happening.