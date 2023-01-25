package com.example.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boot.entities.Moon;
import com.example.boot.entities.MoonThread;
import com.example.boot.exceptions.EntityNotFound;
import com.example.boot.repository.MoonDao;

@Service
public class MoonService {
    
    @Autowired
    private MoonDao moonDao;

    public Moon findByMoonId(int id) {
        Optional<Moon> possibleMoon = this.moonDao.findById(id);
        if(possibleMoon.isPresent()) {
            return possibleMoon.get();
        } else {
            throw new EntityNotFound("Moon not found");
        }
    }
    
    public Moon findByMoonName(String name) {
        Optional<Moon> possibleMoon = this.moonDao.findByMoonName(name);
        if (possibleMoon.isPresent()) {
            return possibleMoon.get();
        } else {
            throw new EntityNotFound("Moon not found");
        }
    }
    
    public List<Moon> findAllMoons() {
        List<Moon> moons = this.moonDao.findAll();
        if (moons.size() != 0) {
            return moons;
        } else {
            throw new EntityNotFound("No moons found in this database");
        }
    }

    public String createMoon(Moon moon) {
        this.moonDao.createMoon(moon.getName(), moon.getMyPlanetId());
        return "moon created";
    }

    public String updateMoon(Moon moon) {
        int rowCount = this.moonDao.updateMoon(moon.getName(), moon.getMyPlanetId(), moon.getId());
        if (rowCount == 1) {
            return "Moon updated successfully";
        } else {
            throw new EntityNotFound("could not update moon");
        }
    }

    public String deleteMoonById(int id) {
        this.moonDao.deleteById(id);
        return "Moon with given id deleted";
    }
}
