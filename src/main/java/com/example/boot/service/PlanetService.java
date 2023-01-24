package com.example.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boot.entities.Planet;
import com.example.boot.exceptions.EntityNotFound;
import com.example.boot.repository.PlanetDao;

@Service
public class PlanetService {

    @Autowired
    private PlanetDao planetDao;

    public Planet findByPlanetId(int id) {
        Optional<Planet> possiblePlanet = this.planetDao.findById(id);
        if (possiblePlanet.isPresent()) {
            return possiblePlanet.get();
        } else {
            throw new EntityNotFound("Planet not found");
        }
    }

    public Planet findByPlanetName(String name) {
        Optional<Planet> possiblePlanet = this.planetDao.findByPlanetName(name);
        if (possiblePlanet.isPresent()) {
            return possiblePlanet.get();
        } else {
            throw new EntityNotFound("Planet not found");
        }
    }

    public List<Planet> findAllPlanets() {
        List<Planet> planets = this.planetDao.findAll();
        if (planets.size() != 0) {
            return planets;
        } else {
            throw new EntityNotFound("No planets found in the database");
        }
    }

    public String createPlanet(Planet planet) {
        this.planetDao.createPlanet(planet.getName(), planet.getOwnerId());
        return "Planet created";
    }

    public String updatePlanet(Planet planet) {
        int rowCount = this.planetDao.updatePlanet(planet.getName(), planet.getOwnerId(), planet.getId());
        if (rowCount == 1) {
            return "Planet updated successfully";
        } else {
            throw new EntityNotFound("could not update planet");
        }
    }

    public String deletePlanetById(int id) {
        this.planetDao.deleteById(id);
        return "Planet with given id deleted";
    }
}
