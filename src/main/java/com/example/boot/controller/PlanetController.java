package com.example.boot.controller;


import java.util.List;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.boot.entities.Planet;
import com.example.boot.exceptions.AuthenticationFailed;
import com.example.boot.exceptions.EntityNotFound;
import com.example.boot.service.PlanetService;

@RestController
public class PlanetController {
    
    private static Logger planetLogger = LoggerFactory.getLogger(PlanetController.class);

    @Autowired
    private PlanetService planetService;

    @ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<String> authenticationFailed(AuthenticationFailed e) {
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e) {
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e) {
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> deleteFailed(EmptyResultDataAccessException e) {
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>("could not delete planet", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/planet/id/{id}")
    public ResponseEntity<Planet> findByPlanetId(@PathVariable int id) {
        return new ResponseEntity<>(this.planetService.findByPlanetId(id), HttpStatus.OK);
    }

    @GetMapping("/api/planet/{name}")
    public ResponseEntity<Planet> findByPlanetName(@PathVariable String name) {
        return new ResponseEntity<>(this.planetService.findByPlanetName(name), HttpStatus.OK);
    }

    @GetMapping("/api/planet")
    public ResponseEntity<List<Planet>> findAllPlanets() {
        return new ResponseEntity<>(this.planetService.findAllPlanets(), HttpStatus.OK);
    }

    @PostMapping("/api/planet")
    public ResponseEntity<String> createMoon(@RequestBody Planet planet) {
        String message = this.planetService.createPlanet(planet);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PatchMapping("/api/planet")
    public ResponseEntity<String> updateMoon(@RequestBody Planet planet) {
        return new ResponseEntity<>(this.planetService.updatePlanet(planet), HttpStatus.OK);
    }

    @DeleteMapping("/api/planet/{id}")
    public ResponseEntity<String> deletePlanetById(@PathVariable int id) {
        return new ResponseEntity<>(this.planetService.deletePlanetById(id), HttpStatus.OK);
    }
}

