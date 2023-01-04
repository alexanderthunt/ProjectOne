package com.example.boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.boot.entities.Planet;

public interface PlanetDao extends JpaRepository<Planet,Integer> {
    
        Optional<Planet> findByPlanetName(String planetName);

        @Transactional
        @Modifying
        @Query(value = "insert into planets values (default, :planetName , :ownerId)", nativeQuery = true)
        void createPlanet(@Param("planetName") String planetName, @Param("ownerId") int ownerId);

        @Transactional
        @Modifying
        @Query(value = "update planets set name = :planetName , ownerid = :ownerId , id = :planetId where id = planetId ", nativeQuery = true)
        int updatePlanet(@Param("planetName") String planetName, @Param("ownerId") int ownerId, @Param("planetId") int planetId);

}
