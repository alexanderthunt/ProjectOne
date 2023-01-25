# Broken Planetarium

# Break
If there has already been 5 successful requests for planets by name, the service layer will return a planet with a null name to the contoller layer. When the contoller layer detects the planet has a null name, it will return a 500 response entity.

This is my method of simulating a request that breaks and sends 500 codes.

# Visual
## Broken
### Broken Planet Controller
```java
    @GetMapping("/api/planet/{name}")
    public ResponseEntity<Planet> findByPlanetName(@PathVariable String name) {
        Planet planet = this.planetService.findByPlanetName(name);
        if (planet.getName()==null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(planet, HttpStatus.OK);
    }


```
### Broken Planet Service
```java
    int numberOfPlanetsReturnedByNameSuccessfully = 0;
    public Planet findByPlanetName(String name) {
        if (numberOfPlanetsReturnedByNameSuccessfully < 5) {
            numberOfPlanetsReturnedByNameSuccessfully++;
            Optional<Planet> possiblePlanet = this.planetDao.findByPlanetName(name);
            if (possiblePlanet.isPresent()) {
                return possiblePlanet.get();
            } else {
                throw new EntityNotFound("Planet not found");
            }
        } else {
            Planet planet = new Planet();
            return planet;
        }
    }
```
## Fixed
### Fixed Planet Controller
```java
    @GetMapping("/api/planet/{name}")
    public ResponseEntity<Planet> findByPlanetName(@PathVariable String name) {
        return new ResponseEntity<>(this.planetService.findByPlanetName(name), HttpStatus.OK);
    }


```
### Fixed Planet Service
```java
    public Planet findByPlanetName(String name) {
        Optional<Planet> possiblePlanet = this.planetDao.findByPlanetName(name);
        if (possiblePlanet.isPresent()) {
            return possiblePlanet.get();
        } else {
            throw new EntityNotFound("Planet not found");
        }
    }
```
# Indicator
Grafana will show increased rates of 500 internal server errors after the application has been up and running for a while.