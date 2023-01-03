package com.example.boot.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moons")
public class Moon {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String moonName;
    @Column(name = "myplanetid")
    private int myPlanetId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return moonName;
    }

    public void setName(String name) {
        this.moonName = name;
    }

    public int getMyPlanetId() {
        return myPlanetId;
    }

    public void setMyPlanetId(int myPlanetId) {
        this.myPlanetId = myPlanetId;
    }

    @Override
    public String toString() {
        return "Moon [id=" + id + ", name=" + moonName + ", myPlanetId=" + myPlanetId + "]";
    }
}
