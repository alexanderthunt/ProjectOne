package com.example.boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.boot.entities.Moon;

public interface MoonDao extends JpaRepository<Moon,Integer> {
    
    Optional<Moon> findByMoonName(String name);
    
    @Transactional
    @Modifying
    @Query(value = "insert into moons values (default, :name , :myPlanetId)", nativeQuery = true)
    void createMoon(@Param("name") String name, @Param("myPlanetId") int myPlanetId);

    @Transactional
    @Modifying
    @Query(value = "update moons set name = :name, myplanetid = :myPlanetId where id = :moonId", nativeQuery = true)
    int updateMoon(@Param("name") String name, @Param("myPlanetId") int myPlanetId, @Param("moonId") int moonId);

}
